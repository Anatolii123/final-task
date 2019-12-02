function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var personApi = Vue.resource('/persons');
var carApi = Vue.resource('/cars');

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
        '<input type="text" class="form-control" placeholder="write name" v-model="name"/><br>' +
        '<input type="date" class="form-control" v-model="birthDate"/><br>' +
        '<input type="button" class="btn btn-outline-primary" value="Save" @click="save"/><br><br>' +
        '</div>',
    methods: {
        save: function () {
            var person = {id: 5, name: this.name, birthDate: this.birthDate};
            this.$http.post('http://localhost:8080/person', person).then(response => {
                response.status;
            });
        }
    }
});

Vue.component('person-row', {
    props: ['person', 'editMethod','persons'],
    template:
        '<div align="center">' +
        '<table class="table">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th>ID</th>\n' +
        '      <th>Name</th>\n' +
        '      <th>Birthday</th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody>\n' +
        '    <tr>\n' +
        '      <th scope="row">{{person.id}}</th>\n' +
        '      <td>{{person.name}}</td>\n' +
        '      <td>{{person.birthDate}}</td>\n' +
        '    </tr>\n' +
        '  </tbody>\n' +
        '</table>' +
        '</div>'
});

Vue.component('persons-list', {
    props: ['persons'],
    data: function () {
        return {
            person: null
        }
    },
    template:
        '<div style="position: relative; width: 300px;">' +
        '<person-form :persons="persons" :personAttr="person"/>' +
        '<person-row v-for="person in persons" :key="person.id" :person="person"' +
            ':persons="persons"/>' +
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
    template: '<persons-list :persons="persons" />',
    data: {
        persons: []
    }
});