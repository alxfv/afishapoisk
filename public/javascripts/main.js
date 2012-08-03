$(document).ready(function() {
  if ($('#info').length) {
    CKEDITOR.replace('info', {
      language: 'ru',
      toolbar: [{
        name: 'basicstyles',
        items: ['Bold', 'Italic', '-', 'NumberedList', 'BulletedList']
      }]
    });
  }
  
//  function saveTime(input, ev) {
//    var s = input.val();
//    console.log(ev);
//  }
//  
//  $('#start').datepicker({
//    format: 'dd.mm.yyyy',
//    weekStart: 1,
//    autoclose: true,
//    language: 'ru'
//  }).on('changeDate', function(ev) {
//    saveTime($(this), ev);
//  }).on('hide', function(ev) {
//    saveTime($(this), ev);
//  }).on('show', function(ev) {
//    saveTime($(this), ev);
//  });
})