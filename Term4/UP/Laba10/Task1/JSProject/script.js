function onClick(){
	var firstName = document.getElementById('first-name').value;
	var secondName = document.getElementById('second-name').value;
	var patronymic = document.getElementById('patronymic').value;
	var mail = document.getElementById('mail').value;
	var e = document.getElementById('select');
	var select = e.options[e.selectedIndex].value;
	var radio = document.querySelector('input[name="radio"]:checked').value;
	var image_src = document.getElementById('image_src').value;
	var massege = document.getElementById('massege').value;
	var checked = document.getElementById("input-checkbox").checked;	
	var m = "<p>" + "Фамилия: " + firstName + "</p>"
	+ "<p>" + "Имя: " + secondName + "</p>"
	+ "<p>" + "Отчество: " + patronymic +"</p>"
	+ "<p>" + "Почта: " + mail +"</p>"
	+ "<p>" + "Учебное заведение: " +  select + "</p>"
	+ "<p>" + "Сейчас вы: " + radio + "</p>" 
	+ "<p>" + "Языки программирования и технологии, которые вы используете: " + "<br>" + massege + "</p>"
	document.getElementById('container-form').innerHTML = m;	
}

function onCancel(){
	
}

var mouseOver = function(str){
  return function(){
    document.getElementById(str).style.background = 'gray';
  }
}

var mouseOut = function(str){
  return function(){
    document.getElementById(str).style.background = '';
  }
}

for(i = 1; i <= 3; i++){
  document.getElementById(i).addEventListener("mouseover", mouseOver(i));
  document.getElementById(i).addEventListener("mouseout", mouseOut(i));
}


