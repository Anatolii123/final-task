Vue.component('person-row', {
    props: ['person'],
    template: '<div><i>({{person.id}})</i> | {{person.name}} | {{person.birthdate}}</div>'
});

Vue.component('persons-list', {
    props: ['persons'],
    template: '<div><person-row v-for="person in persons" :key="person.id" :person="person"/></div>'
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
});