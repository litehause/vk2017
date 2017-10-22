class ExhibitEdit
  constructor: (@container) ->
    @initFileUploader()
    @initThumbmailUploader()

  initThumbmailUploader: =>
    this.ThumbmailUploader = $('#thumbMailfileUploader').fileupload
      dataType: "json"
      sequentialUploads: true
      replaceFileInput: false
      add: (e, data) ->
        data.submit()
      done: (e, data) =>
        @ThumbmailUploader.empty()
        response = data.response().result
        $("#thumbMailFileId").val(response.id)
        $("#thumbMailFileName").text(response.name)

        $("#addThumbMailFile").hide()
        $("#deleteThumbMailFile").show();
      fail: (e, data) =>
        alert("fail upload region main image")
    $('#addThumbMailFile').click =>
      @ThumbmailUploader.val("")
      @ThumbmailUploader.empty()
      this.ThumbmailUploader.click()
    $("#deleteThumbMailFile").click =>
      $("#addThumbMailFile").show()
      $("#deleteThumbMailFile").hide();
      $("#thumbMailFileId").val("")
      $("#thumbMailFileName").text("")


  initFileUploader: =>
    this.fileUploader = $('#fileUploader').fileupload
      dataType: "json"
      sequentialUploads: true
      replaceFileInput: false
      add: (e, data) ->
        data.submit()
      done: (e, data) =>
        @fileUploader.empty()
        response = data.response().result
        $("#fileId").val(response.id)
        $("#fileName").text(response.name)
        #$("#regionMainImage").attr("src", response.data.origFileUrl)
        $("#addFile").hide()
        $("#deleteFile").show();
      fail: (e, data) =>
        alert("fail upload region main image")
    $('#addFile').click =>
      @fileUploader.val("")
      @fileUploader.empty()
      this.fileUploader.click()
    $("#deleteFile").click =>
      $("#addFile").show()
      $("#deleteFile").hide();
      $("#fileId").val("")
      $("#fileName").text("")

$ ->
  new ExhibitEdit("#exhibitEdit")