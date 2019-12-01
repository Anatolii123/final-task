function sendRequest() {
    var id = document.getElementById("personId");
    var xhr = new XMLHttpRequest();
    var param = 'personid=' + id;
    xhr.open("GET", 'http://localhost:8080/submit?' + param, true);
    xhr.send();
}
