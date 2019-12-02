var personaApi = Vue.resource('/persons')

Vue.component('persona-row', {
    props: ['persona'],
    template: '<tr><th scope="row">{{persona.id}}</th><td>{{persona.name}}</td><td>{{persona.birthDate}}</td></tr>'
});

Vue.component('personas-list', {
    props: ['personas'],
    template: '<div align="center" style="align-content:center; width: 400px">' +
        '<table class="table" align="center">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th>ID</th>\n' +
        '      <th>Name</th>\n' +
        '      <th>Birthday</th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody>\n' +
        '<persona-row v-for="persona in personas" :key="persona.id" :persona="persona"/>' +
        '  </tbody>\n' +
        '</table>' +
        '</div>',
    created: function () {
        personaApi.get().then(result =>
            result.json().then(data =>
                data.forEach(persona => this.personas.push(persona))
            )
        )
    }
});

var app = new Vue({
    el: '#app2',
    template: '<personas-list :personas="personas"/>',
    data: {
        personas: []
    }
});