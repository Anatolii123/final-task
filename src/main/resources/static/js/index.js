function sendRequest() {
    var id = document.getElementById("personId");
    var xhr = new XMLHttpRequest();
    var param = 'personid=' + id;
    // xhr.open("GET", 'http://localhost:8080/personwithcars?' + param, true);
    // xhr.send();
    fetch('http://localhost:8080/personwithcars?' + param, {
        method: 'get',
    })
        .then(
            function (response) {
                if (response.status !== 200) {
                    return;
                }
            }
        )
        .catch(function (err) {
            console.log(err);
        });
}
