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
    template: '<div align="center"><persons-list :persons="persons" /></div>',
    data: {
        persons: []
    }
});