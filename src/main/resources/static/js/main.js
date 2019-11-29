var personApi = Vue.resource('/person{/id}')

Vue.component('person-form', {
    props: ['persons'],
    data: function () {
        return {
            name: '',
            birthdate: ''
        }
    },
    template:
        '<div>' +
            '<input type="text" placeholder="write name" v-model="name"/>' +
            '<input type="date" v-model="birthdate"/>' +
            '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var person = {name: this.name, birthdate: this.birthdate};

            personApi.save({},person).then(result =>
                result.json().then(data => {
                    this.persons.push(data);
                })
            )
        }
    }
});

Vue.component('person-row', {
    props: ['person'],
    template: '<div><i>({{person.id}})</i> | {{person.name}} | {{person.birthdate}}</div>'
});

Vue.component('persons-list', {
    props: ['persons'],
    template:
        '<div>'+
            '<person-form :persons="persons"/>'+
            '<person-row v-for="person in persons" :key="person.id" :person="person"/>'+
        '</div>',
    created: function () {
        personApi.get().then(result =>
            result.json().then(data =>
                data.forEach(person => this.persons.push(person))
            )
        )
    }
})

var app = new Vue({
    el: '#app',
    template: '<persons-list :persons="persons" />',
    data: {
        persons: []
    }
});