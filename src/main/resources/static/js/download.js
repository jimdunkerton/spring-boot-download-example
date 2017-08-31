$(document).ready(function () {

  $('button').click(function (clickEvent) {
    var buttonText = clickEvent.currentTarget.textContent;
    var mediaType = getMediaType(buttonText);
    var pathParam = getPathParam(buttonText);
    doRequest(mediaType, pathParam);
  });

  function getMediaType(buttonText) {
    if (buttonText.indexOf('CSV') > -1) {
      return 'text/csv';
    } else {
      return 'application/pdf';
    }
  }

  function getPathParam(buttonText) {
    if (buttonText.indexOf('entities') > -1) {
      return '/entities';
    } else if (buttonText.indexOf('InputStreamResource') > -1) {
      return '/isr';
    } else if (buttonText.indexOf('Byte') > -1) {
      return '/bytes';
    } else {
      return '/servletresponse';
    }
  }

  function doRequest(mediaType, pathParam) {
    $.ajax({
      headers: {
        Accept: mediaType
      },
      url: 'download' + pathParam,
      success: function (data, status, response) {
        var filename = getFilename(response) || 'people';
        var a = document.createElement('a');
        var blob = new Blob([data], {'type': mediaType});
        var objectURL = window.URL.createObjectURL(blob);
        console.log(objectURL);
        a.href = objectURL;
        a.download = filename;
        a.click();
        window.URL.revokeObjectURL(objectURL);
      },
      error: function () {
        alert('Error downloading file');
      }
    });
  }

  function getFilename(response) {
    var contentDispositionHeader = response.getResponseHeader("Content-Disposition");
    if (contentDispositionHeader) {
      return contentDispositionHeader.split('=').slice(-1).pop();
    }

    return null;
  }

});