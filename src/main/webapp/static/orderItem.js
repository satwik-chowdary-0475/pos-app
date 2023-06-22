var orderId;
var orderStatus;
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

function getOrderStatus(){

    var url = getOrderUrl();
    $.ajax({
        url:url,
        type: 'GET',
        success: function(data){
            orderStatus = data.status;
            disableButtons(orderStatus);
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
    	for(var i in data){
    		var e = data[i];
    		var status = e.status;
    		var editHtml = '<button class="btn btn-primary" onclick="displayEditOrderItem('+e.id+')"';
    		editHtml+=(orderStatus != 'ACTIVE')?' disabled':'';
    		editHtml+='>edit</button>';
    		var deleteHtml = '<button class="btn btn-primary" onclick="deleteOrderItem('+e.id+')"';
    		deleteHtml+=(orderStatus!='ACTIVE')?' disabled':'';
    		deleteHtml+='>delete</button>';
    		var buttonHtml = editHtml + '&nbsp' + deleteHtml;
    		var row = '<tr>'
    		+ '<td>' + e.id + '</td>'
    		+ '<td>' + e.orderId + '</td>'
    		+ '<td>' + e.productId + '</td>'
    		+ '<td>' + e.quantity + '</td>'
    		+ '<td>' + e.sellingPrice + '</td>'
    		+ '<td>' + buttonHtml + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}

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
    console.log("Invoice printed!!!");
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


function init(){
    $("#add-orderItem").click(addOrderItem);
    $("#update-orderItem").click(updateOrderItem);
    $("#print-invoice").click(printInvoice);
}

$(document).ready(getOrderId());
$(document).ready(getOrderStatus());
$(document).ready(init);
$(document).ready(getOrderItemList());