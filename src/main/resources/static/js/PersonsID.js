var personaApi = Vue.resource('/persons');

Vue.component('persona-row', {
    props: ['persona'],
    template: '<option v-bind:value="persona.id">{{persona.id}} - {{persona.name}}</option>'
});

Vue.component('personas-list', {
    props: ['personas'],
    template:
        '   <select class="form-control" name="personid" id="personid" style="width: 150px">' +
        '       <persona-row v-for="persona in personas" :key="persona.id" :persona="persona"/>' +
        '   </select>',
    created: function () {
        personaApi.get().then(result =>
            result.json().then(data =>
                data.forEach(persona => this.personas.push(persona))
            )
        )
    }
});

var app = new Vue({
    el: '#app3',
    template: '<personas-list :personas="personas" />',
    data: {
        personas: []
    }
});