var carApi = Vue.resource('/cars');

Vue.component('cara-row', {
    props: ['cara'],
    template: '<tr><th scope="row">{{cara.id}}</th><td>{{cara.model}}</td><td>{{cara.horsepower}}</td><td>{{cara.ownerId}}</td></tr>'
});

Vue.component('caras-list', {
    props: ['caras'],
    template: '<div align="center" style="align-content:center; width: 400px">' +
        '<table class="table" align="center" style="text-align: center">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th>ID</th>\n' +
        '      <th>Model</th>\n' +
        '      <th>Horsepower</th>\n' +
        '      <th>Owner ID</th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody>\n' +
        '<cara-row v-for="cara in caras" :key="cara.id" :cara="cara"/>' +
        '  </tbody>\n' +
        '</table>' +
        '</div>',
    created: function () {
        carApi.get().then(result =>
            result.json().then(data =>
                data.forEach(cara => this.caras.push(cara))
            )
        )
    }
});

var app = new Vue({
    el: '#app4',
    template: '<div align="center"><caras-list :caras="caras"/></div>',
    data: {
        caras: []
    }
});