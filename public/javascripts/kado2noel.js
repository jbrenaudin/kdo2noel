function escapeHtml(unsafe) {
  return unsafe
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;")
      .replace(/"/g, "&quot;")
      .replace(/'/g, "&#039;");
}

function ouvrePopupDeConfirmation(titre, message, siOK) { 
	$('#titrePopupDeConfirmation').text(titre);
	$('#messagePopupDeConfirmation').text(message);
	$('#okPopupDeConfirmation').attr('href', siOK);
	$('#popupDeConfirmation').modal('show');
}

function fermePopupDeConfirmation() {
	$('#popupDeConfirmation').modal('hide');
}