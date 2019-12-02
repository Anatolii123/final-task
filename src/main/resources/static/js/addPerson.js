var personApi = Vue.resource('/persons');

Vue.component('person-row', {
    props: ['person'],
    template: '<tr><th scope="row">{{person.id}}</th><td>{{person.name}}</td><td>{{person.birthDate}}</td></tr>'
});

Vue.component('person-form', {
    props: ['persons', 'personAttr'],
    data: function () {
        return {
            name: '',
            birthDate: '',
            id: ''
        }
    },
    watch: {
        personAttr: function (newVal, oldVal) {
            this.name = newVal.name;
            this.birthDate = newVal.birthDate;
            this.id = newVal.id;
        }
    },
    template:
        '<div align="center">' +
        'Name: <input type="text" class="form-control" placeholder="Write name" v-model="name"/><br>' +
        'Birthday: <input type="date" class="form-control" v-model="birthDate"/><br>' +
        '<input type="button" class="btn btn-outline-primary" value="Save" @click="save"/><br><br>' +
        '</div>',
    methods: {
        save: function () {
            var person = {id: 5, name: this.name, birthDate: this.birthDate};
            this.$http.post('http://localhost:8080/person', person).then(response => {
                this.personas.push(person);
            });
            this.name = '';
            this.birthDate = ''
        }
    }
});

Vue.component('persons-list', {
    props: ['persons'],
    template:
        '<div align="center" style="width: 300px;">' +
        '<person-form :persons="persons" :personAttr="person"/><br>' +
        '<table class="table" align="center" style="text-align: center">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th>ID</th>\n' +
        '      <th>Name</th>\n' +
        '      <th>Birthday</th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody>\n' +
        '<person-row v-for="person in persons" :key="person.id" :person="person"/>' +
        '  </tbody>\n' +
        '</table>' +
        '</div>',
    created: function () {
        personApi.get().then(result =>
            result.json().then(data =>
                data.forEach(person => this.persons.push(person))
            )
        )
    }
});

var app = new Vue({
    el: '#app',
    template: '<div align="center"><persons-list :persons="persons" /></div>',
    data: {
        persons: []
    }
});