Vue.component('persons-list', {
    props: ['persons'],
    template: '<div><div v-for="person in persons">{{person.name}} {{person.birthdate}}</div></div>'
})

var app = new Vue({
    el: '#app',
    template: '<persons-list :persons="persons" />',
    data: {
        persons: [
            {id: '1', name: 'A', birthdate: '1990-05-05'},
            {id: '2', name: 'B', birthdate: '1990-05-05'},
            {id: '3', name: 'C', birthdate: '1990-05-05'},
        ]
    }
})