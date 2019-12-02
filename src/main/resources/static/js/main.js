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
        '<input type="text" class="form-control" placeholder="write name" v-model="name"/>' +
        '<input type="date" class="form-control" v-model="birthDate"/>' +
        '<input type="button" class="btn btn-outline-primary" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            // var xhr = new XMLHttpRequest();
            var person = {id: 5, name: this.name, birthDate: this.birthDate};
            // xhr.open('post', 'http://localhost:8080/person', true);
            // xhr.setRequestHeader("Content-Type","application/json");
            // xhr.send(person.json);
            // fetch('http://localhost:8080/person', {
            //     method: 'post',
            //     headers: {
            //         "Content-Type": "application/json"
            //     },
            //     body: {
            //         "id":4,
            //         "name":"Name",
            //         "birthDate":"2006-05-25"
            //     }
            // })
            //     .then(
            //         function(response) {
            //             if (response.status !== 200) {
            //                 return;
            //             }
            //         }
            //     )
            //     .catch(function(err) {
            //         console.log(err);
            //     });
            this.$http.post('http://localhost:8080/person', person).then(response => {
                response.status;
            });
            // personApi.save(person.json, person).then(result =>
            //     result.then(data => {
            //         this.persons.add(data);
            //     })
            // )
        }
    }
});

Vue.component('person-row', {
    props: ['person', 'editMethod','persons'],
    template:
        '<div align="center">' +
        '<i>({{person.id}})</i> | {{person.name}} | {{person.birthDate}}' +
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
            ':editMethod="editMethod" :persons="persons"/>' +
        '</div>',
    created: function () {
        personApi.get().then(result =>
            result.json().then(data =>
                data.forEach(person => this.persons.push(person))
            )
        )
    },
    methods: {
        editMethod: function (person) {
            this.person = person;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<persons-list :persons="persons" />',
    data: {
        persons: []
    }
});