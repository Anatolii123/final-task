var carApi = Vue.resource('/cars');
var personoApi = Vue.resource('/persons');

function checkInput(input) {
    input.value = input.value.replace(/[^\d,]/g, '')
};

Vue.component('persono-row', {
    props: ['persono'],
    template: '<option v-bind:value="persono.id">{{persono.id}} - {{persono.name}}</option>'
});

Vue.component('personos-list', {
    props: ['personos'],
    template:
        '   <select class="form-control" v-model="ownerId" value="1" style="width: 150px">' +
        '       <persono-row v-for="persono in personos" :key="persono.id" :persono="persono"/>' +
        '   </select>',
    created: function () {
        personoApi.get().then(result =>
            result.json().then(data =>
                data.forEach(persono => this.personos.push(persono))
            )
        )
    }
});

Vue.component('car-row', {
    props: ['car'],
    template: '<tr><th scope="row">{{car.id}}</th><td>{{car.model}}</td><td>{{car.horsepower}}</td><td>{{car.ownerId}}</td></tr>'
});

Vue.component('cars-list', {
    props: ['cars'],
    template:
        '<div align="center" style="width: 400px;">' +
        '<table class="table" align="center" style="text-align: center">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th>ID</th>\n' +
        '      <th>Model</th>\n' +
        '      <th>Horsepower</th>\n' +
        '      <th>Owner ID</th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody>\n' +
        '<car-row v-for="car in cars" :key="car.id" :car="car"/>' +
        '  </tbody>\n' +
        '</table>' +
        '</div>',
    created: function () {
        carApi.get().then(result =>
            result.json().then(data =>
                data.forEach(car => this.cars.push(car))
            )
        )
    }
});

var app = new Vue({
    el: '#app2',
    data: function () {
        return {
            model: '',
            horsepower: '',
            ownerId: '',
            id: ''
        }
    },
    watch: {
        carAttr: function (newVal) {
            this.model = newVal.model;
            this.horsepower = newVal.horsepower;
            this.ownerId = newVal.ownerId;
            this.id = newVal.id;
        }
    },
    template: '<div align="center">' +
        '<div align="center" style="width: 400px">' +
        '<div style="display: table-cell;"><b style="margin-right: 2px;">Model:</b></div>' +
        '<div style="display: table-cell;"><input type="text" class="form-control" placeholder="Write model" ' +
        'required="required" v-model="model" style="margin-right: 2px; margin-left: 2px; width: 152px"/></div>' +
        '<div style="display: table-cell;"><b style="margin-right: 2px; margin-left: 2px">Hp:</b></div>' +
        '<div style="display: table-cell;">' +
        '   <input type="text" class="form-control" placeholder="Write horsepower" onkeyup="return checkInput(this);" ' +
        '   onchange="return checkInput(this);" required="required" v-model="horsepower" style="margin-left: 2px; width: 156px"/>' +
        '</div><br>' +
        '<div style="display: table-cell;"><b style="margin-right: 2px;">Person:</b></div>' +
        '<div style="display: table-cell;">' +
        '   <personos-list placeholder="Choose person" :personos="personos" required="required" style="margin-left: 2px; width: 341px"/>' +
        '</div><br>' +
        '<input type="button" class="btn btn-outline-primary" value="Save" @click="save"/>' +
        '</div><br>' +
        '<cars-list :cars="cars" />' +
        '</div>',
    methods: {
        save: function () {
            var car = {id: 5, model: this.model, horsepower: this.horsepower, ownerId: this.$children[0].ownerId};
            this.$http.post('http://localhost:8080/2/car', car).then(response => {
                this.cars.push(response.body);
                this.model = '';
                this.horsepower = '';
            });
            this.ownerId = '';
        }
    },
    data: {
        cars: [],
        personos: []
    }
});