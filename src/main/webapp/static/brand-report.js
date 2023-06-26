function getBrandUrl(){
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/report";
}

function displayBrandDetails(data){

    var $tbody = $('#brand-table').find('tbody');
    	$tbody.empty();
    	for(var i in data){
    		var e = data[i];
            i = parseInt(i)+1;
    		var row = '<tr>'
    		+ '<td>' + i + '</td>'
    		+ '<td>'  + e.brand + '</td>'
    		+ '<td>'  + e.category + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}

}

function getBrandReports(){

    var url = getBrandUrl() + '/brand-report';
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		displayBrandDetails(data);
    	   },
    	   error: handleAjaxError
    	});


}

$(document).ready(getBrandReports);
