var orderId;
var orderStatus;
var totalPrice = 0;
function getOrderId(){
    orderId = $("#order-item").attr("orderId")
}

function disableButtons(status){
    if(status!= 'ACTIVE'){
        $("#add-orderItem").prop('disabled',true);
    }
}

function getOrderUrl(){
   var baseUrl = $("meta[name=baseUrl]").attr("content")
   return baseUrl + "/api/order/"+orderId;
}
function getOrderItemUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/order/"+orderId+"/order-items";
}


// TODO: FUNCTIONS CALL ASYNC
function getOrderDetails(){

    var url = getOrderUrl();
    $.ajax({
        url:url,
        type: 'GET',
        success: function(data){
            orderStatus = data.status;
            disableButtons(orderStatus);
            displayOrderDetails(data);
        },
        error: handleAjaxError
    });
    return orderStatus;
}

function getOrderItemList(){
var url = getOrderItemUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItemList(data);
	   },
	   error: handleAjaxError
	});
}

function displayEditOrderItem(id){
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItem(data);
	   },
	   error: handleAjaxError
	});
}

function displayOrderItem(data){
	$("#orderItem-edit-form input[name=quantity]").val(data.quantity);
	$("#orderItem-edit-form input[name=barcode]").val(data.barcode);
	$("#orderItem-edit-form input[name=sellingPrice]").val(data.sellingPrice);
	$("#orderItem-edit-form input[name=id]").val(data.id);
	$('#edit-orderItem-modal').modal('toggle');
}

function deleteOrderItem(id){
    var url = getOrderItemUrl()+"/"+id;
    $.ajax({
    	   url: url,
    	   type: 'DELETE',
    	   success: function(data) {
    	   		displayOrderItemList(data);
    	   },
    	   error: handleAjaxError
    	});
}

function displayOrderItemList(data){
    var $tbody = $('#orderItem-table').find('tbody');
    	$tbody.empty();
    	totalPrice = 0;
    	for(var i in data){
    		var e = data[i];
    		var editHtml = '<button class="btn btn-primary" onclick="displayEditOrderItem('+e.id+')"';
    		editHtml+=(orderStatus != 'ACTIVE')?' disabled':'';
    		editHtml+='>edit</button>';
    		var deleteHtml = '<button class="btn btn-danger" onclick="deleteOrderItem('+e.id+')"';
    		deleteHtml+=(orderStatus!='ACTIVE')?' disabled':'';
    		deleteHtml+='>delete</button>';
    		var buttonHtml = editHtml + '&nbsp' + deleteHtml;
    		i = parseFloat(i)+1;
    		var row = '<tr>'
    		+ '<td>' + i + '</td>'
    		+ '<td>' + e.productName + '</td>'
    		+ '<td>' + e.quantity + '</td>'
    		+ '<td>' + e.sellingPrice + '</td>'
    		+ '<td>' + buttonHtml + '</td>'
    		+ '</tr>';
            $tbody.append(row);
            totalPrice+=(parseInt(e.sellingPrice)*parseInt(e.quantity));
    	}

    	var $totalPriceElement = $('#total-price');
    	$totalPriceElement.empty();
    	$totalPriceElement.append('<h5 class="fw-bold mb-0">Total : </h5>');
    	$totalPriceElement.append('<h5 class="fw-bold mb-0">'+totalPrice+'</h5>');

}

function addOrderItem(event){
	//Set the values to update
	var $form = $("#orderItem-form");
	var json = toJson($form);
	var url = getOrderItemUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getOrderItemList();
	   		$("#orderItem-form input[name=barcode]").val('');
            $("#orderItem-form input[name=quantity]").val('');
            $("#orderItem-form input[name=sellingPrice]").val('');

   },
	   error:handleAjaxError
	});

	return false;
}

function updateOrderItem(event){
	$('#edit-orderItem-modal').modal('toggle');
	//Get the ID
	var id = $("#orderItem-edit-form input[name=id]").val();
	var url = getOrderItemUrl() + "/" + id;

	//Set the values to update
	var $form = $("#orderItem-edit-form");
	var json = toJson($form);
	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getOrderItemList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function printInvoice(){
    if(orderStatus == "ACTIVE"){
        var url = getOrderUrl();
            $.ajax({
                url:url,
                type:'PUT',
                success : function(){
                    window.location=document.referrer;
                },
                error: handleAjaxError
            });
    }

}

function displayOrderDetails(data){
    var orderTitle = "Order #"+data.id
    var $title = $("#order-title").find("h3");
    $title.html(orderTitle);
    var $details = $("#order-details").find("div");
    var date = new Date(data.createdAt*1000);
    var customerName = '<span class="mr-3 pb-2">'+data.customerName+'</span>'
    var orderTime = '<span class="mr-3 pb-2">'+date.toLocaleDateString()+'</span>'
    $details.append(customerName);
    $details.append(orderTime);
}

function init(){
    $("#add-orderItem").click(addOrderItem);
    $("#update-orderItem").click(updateOrderItem);
    $("#print-invoice").click(printInvoice);
}

$(document).ready(getOrderId());
$(document).ready(getOrderDetails());
$(document).ready(init);
$(document).ready(getOrderItemList());