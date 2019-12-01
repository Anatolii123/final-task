var id = document.getElementById("personId");
var xhr = new XMLHttpRequest();
var param = 'personid=' + id;

function sendRequest() {
    xhr.open("GET", 'http://localhost:8080/submit?' + param, true);
    xhr.send();
}
