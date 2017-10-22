class ExhibitionEdit
  constructor: (@container) ->
    @initSelectExhibit()
    @initImageUploader()

  initImageUploader:() =>
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

  initSelectExhibit: () =>
    $('#selectExhibit').select2();

$ ->
  new ExhibitionEdit($("#exhibitionEdit"))