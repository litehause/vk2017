// Generated by CoffeeScript 1.12.7
var ExhibitEdit,
  bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

ExhibitEdit = (function() {
  function ExhibitEdit(container) {
    this.container = container;
    this.initFileUploader = bind(this.initFileUploader, this);
    this.initThumbmailUploader = bind(this.initThumbmailUploader, this);
    this.initFileUploader();
    this.initThumbmailUploader();
  }

  ExhibitEdit.prototype.initThumbmailUploader = function() {
    this.ThumbmailUploader = $('#thumbMailfileUploader').fileupload({
      dataType: "json",
      sequentialUploads: true,
      replaceFileInput: false,
      add: function(e, data) {
        return data.submit();
      },
      done: (function(_this) {
        return function(e, data) {
          var response;
          _this.ThumbmailUploader.empty();
          response = data.response().result;
          $("#thumbMailFileId").val(response.id);
          $("#thumbMailFileName").text(response.name);
          $("#addThumbMailFile").hide();
          return $("#deleteThumbMailFile").show();
        };
      })(this),
      fail: (function(_this) {
        return function(e, data) {
          return alert("fail upload region main image");
        };
      })(this)
    });
    $('#addThumbMailFile').click((function(_this) {
      return function() {
        _this.ThumbmailUploader.val("");
        _this.ThumbmailUploader.empty();
        return _this.ThumbmailUploader.click();
      };
    })(this));
    return $("#deleteThumbMailFile").click((function(_this) {
      return function() {
        $("#addThumbMailFile").show();
        $("#deleteThumbMailFile").hide();
        $("#thumbMailFileId").val("");
        return $("#thumbMailFileName").text("");
      };
    })(this));
  };

  ExhibitEdit.prototype.initFileUploader = function() {
    this.fileUploader = $('#fileUploader').fileupload({
      dataType: "json",
      sequentialUploads: true,
      replaceFileInput: false,
      add: function(e, data) {
        return data.submit();
      },
      done: (function(_this) {
        return function(e, data) {
          var response;
          _this.fileUploader.empty();
          response = data.response().result;
          $("#fileId").val(response.id);
          $("#fileName").text(response.name);
          $("#addFile").hide();
          return $("#deleteFile").show();
        };
      })(this),
      fail: (function(_this) {
        return function(e, data) {
          return alert("fail upload region main image");
        };
      })(this)
    });
    $('#addFile').click((function(_this) {
      return function() {
        _this.fileUploader.val("");
        _this.fileUploader.empty();
        return _this.fileUploader.click();
      };
    })(this));
    return $("#deleteFile").click((function(_this) {
      return function() {
        $("#addFile").show();
        $("#deleteFile").hide();
        $("#fileId").val("");
        return $("#fileName").text("");
      };
    })(this));
  };

  return ExhibitEdit;

})();

$(function() {
  return new ExhibitEdit("#exhibitEdit");
});