function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}



function deleteOrder(id){

    var url = getOrderUrl()+"/"+id;
    $.ajax({
    	   url: url,
    	   type: 'DELETE',
    	   success: function() {
    	   		getOrderList();
    	   },
    	   error: handleAjaxError
    	});

}

function printInvoice(id,status){

    console.log("Invoice printed!!!");
    if(status == "ACTIVE"){
        var url = getOrderUrl()+"/"+id;
        $.ajax({
            url:url,
            type:'PUT',
            success : function(){
                getOrderList();
            },
            error: handleAjaxError
        });
    }
}

function displayOrderList(data){
    var $tbody = $('#order-table').find('tbody');
    	$tbody.empty();
    	for(var i in data){
    		var e = data[i];
    		var status = e.status;
    		var deleteHtml = '<button class="btn btn-primary" onclick="deleteOrder('+e.id+')"';
    		deleteHtml += (e.status != 'ACTIVE')?' disabled':'';
    		deleteHtml+=' >Delete</button>';
    		var buttonHtml = '<a class="btn btn-primary" href="order/'+e.id+'/order-items">View</a>';
    		buttonHtml += '&nbsp' + '<button class = "btn btn-primary" onclick="printInvoice('+e.id+','+e.status+')">Print</button>';
    		buttonHtml += '&nbsp'+deleteHtml;
    		var row = '<tr rowId='+e.id+' >'
    		+ '<td>' + e.id + '</td>'
    		+ '<td>' + e.customerName + '</td>'
    		+ '<td>' + e.status + '</td>'
    		+ '<td>' + buttonHtml + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
}

function getOrderList(){
    var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
}

function createOrder(event){
	var $form = $("#order-form");
	var json = toJson($form);
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getOrderList();
   },
	   error:handleAjaxError
	});
	return false;
}

function init(){
    $('#create-order').click(createOrder);
}
$(document).ready(init);
$(document).ready(getOrderList);