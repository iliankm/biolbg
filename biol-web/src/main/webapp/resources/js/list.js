function check_uncheck_all_in_document(ElementCheckAll) {
  var c = new Array();
  c = document.getElementsByTagName('input');
  for (var i = 0; i < c.length; i++) {
    if (c[i].type == 'checkbox') {
      c[i].checked = ElementCheckAll.checked;
    }
  }
  return true;
}
