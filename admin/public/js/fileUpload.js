// Generated by CoffeeScript 1.12.7
var MyFileUploader;

MyFileUploader = (function() {
  function MyFileUploader(container1) {
    this.container = container1;
  }

  return MyFileUploader;

})();

$(function() {
  var container;
  container = $("#fileUploader");
  if (container.length === 1) {
    return new MyFileUploader(container);
  }
});
