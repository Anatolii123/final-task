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

Vue.component('persons-list', {
    props: ['persons'],
    data: function () {
        return {
            person: null
        }
    },
    template:
        '<div align="center" style="width: 300px;">' +
        '<person-form :persons="persons" :personAttr="person"/>' +
        '</div>',
});

var app = new Vue({
    el: '#app',
    template: '<persons-list :persons="persons" />',
    data: {
        persons: []
    }
});