function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var personApi = Vue.resource('/person{/id}')

Vue.component('person-form', {
    props: ['persons', 'personAttr'],
    data: function () {
        return {
            name: '',
            birthdate: '',
            id: ''
        }
    },
    watch: {
        personAttr: function (newVal, oldVal) {
            this.name = newVal.name;
            this.birthdate = newVal.birthdate;
            this.id = newVal.id;
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

            if (this.id) {
                personApi.update({id: this.id}, person).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.persons, data.id);
                        this.persons.splice(index, 1, data);
                        this.name = '';
                        this.birthdate = ''
                    })
                )
            } else {
                personApi.save({}, person).then(result =>
                    result.json().then(data => {
                        this.persons.push(data);
                        this.name = '';
                        this.birthdate = ''
                    })
                )
            }
        }
    }
});

Vue.component('person-row', {
    props: ['person', 'editMethod'],
    template:
        '<div>' +
        '<i>({{person.id}})</i> | {{person.name}} | {{person.birthdate}}' +
        '<span>' +
        '<input type="button" value="Edit" @click="edit"/>' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.person);
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
        '<div>' +
        '<person-form :persons="persons" :personAttr="person"/>' +
        '<person-row v-for="person in persons" :key="person.id" :person="person" :editMethod="editMethod"/>' +
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