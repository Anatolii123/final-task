function sendRequest() {
    var id = document.getElementById("personId");
    var xhr = new XMLHttpRequest();
    var param = 'personid=' + id;
    // xhr.open("GET", 'http://localhost:8080/personwithcars?' + param, true);
    // xhr.send();
    // this.$http.get('http://localhost:8080/personwithcars?' + param).then(response => {
    //     response.status;
    // });
    return "/personwithcars?personid=" + param;
}
