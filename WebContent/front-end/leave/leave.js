$(document).ready(function(){
    var formLeave =  document.getElementById('form-leave');

    $('#table-leave').dataTable({
        responsive: true,
        language:{url:`${ContextPath}/SmartAdmin4/js/datatable/lang/tw.json`}
    });
});