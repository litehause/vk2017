class Excursion
  constructor: (@container) ->
    $('#selectExhibit').select2();
    @initFileUploader()
    @initFileUploaderImage()


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

  initFileUploaderImage: =>
    this.fileUploaderImage = $('#fileUploaderImage').fileupload
      dataType: "json"
      sequentialUploads: true
      replaceFileInput: false
      add: (e, data) ->
        data.submit()
      done: (e, data) =>
        @fileUploaderImage.empty()
        response = data.response().result
        $("#imageId").val(response.id)
        $("#fileNameImage").text(response.name)
        #$("#regionMainImage").attr("src", response.data.origFileUrl)
        $("#addFileImage").hide()
        $("#deleteFileImage").show();
      fail: (e, data) =>
        alert("fail upload region main image")
    $('#addFileImage').click =>
      @fileUploaderImage.val("")
      @fileUploaderImage.empty()
      this.fileUploaderImage.click()
    $("#deleteFileImage").click =>
      $("#addFileImage").show()
      $("#deleteFileImage").hide();
      $("#imageId").val("")
      $("#fileNameImage").text("")



$ ->
  new Excursion($("#excursionEdit"))