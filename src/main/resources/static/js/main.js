var personApi = Vue.resource('/person{/id}')

Vue.component('person-row', {
    props: ['person'],
    template: '<div><i>({{person.id}})</i> | {{person.name}} | {{person.birthdate}}</div>'
});

Vue.component('persons-list', {
    props: ['persons'],
    template:
        '<div>'+
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