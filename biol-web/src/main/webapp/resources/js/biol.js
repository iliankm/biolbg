function formKeyPressed(e,elementIdOnEnterPressed) {
	var keycode;
	if(window.event) { // IE
		keycode = e.keyCode;
    }
	else if(e.which) { // Netscape/Firefox/Opera
		keycode = e.which;
	}
	if (keycode == 13) {
		if (document.activeElement.type=='textarea') {
			return true;
		} else {
			if (elementIdOnEnterPressed=='') {
				return false;
			}
			document.getElementById(elementIdOnEnterPressed).click();
			return false;
		}
	} else  return true;
}

function tipDivTag(divId,imageUrl) {
	var divTag;
	divTag = document.getElementById(divId);
	divTag.innerHTML = '<img src=' + imageUrl + ' width=110>';
	return true;
}

function showDivTag(divId) {
	var divTag;
	divTag = document.getElementById(divId);
	if (divTag.style.display == 'none') {
		divTag.style.display = 'block';
	}
	var px=mousePosX+15;
	var py=mousePosY+15;
	px=px+'px';
	py=py+'px';
	divTag.style.left = px;
	divTag.style.top = py;
	return true;
}

function hideDivTag(divId) {
	var divTag;
	divTag = document.getElementById(divId);
	divTag.style.display= 'none';
	return true;
}