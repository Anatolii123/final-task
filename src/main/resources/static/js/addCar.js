Vue.component('car-form', {
    props: ['cars', 'carAttr'],
    data: function () {
        return {
            model: '',
            horsepower: '',
            ownerId: '',
            id: ''
        }
    },
    watch: {
        personAttr: function (newVal, oldVal) {
            this.model = newVal.model;
            this.horsepower = newVal.horsepower;
            this.ownerId = newVal.ownerId;
            this.id = newVal.id;
        }
    },
    template:
        '<div align="center">' +
        '<input type="text" class="form-control" placeholder="Write model (e.g., BMW-X5)" v-model="model"/><br>' +
        '<input type="text" class="form-control" placeholder="Write horsepower" v-model="horsepower"/><br>' +
        '<input type="text" class="form-control" placeholder="Write owner ID" v-model="ownerId"/><br>' +
        '<input type="button" class="btn btn-outline-primary" value="Save" @click="save"/><br><br>' +
        '</div>',
    methods: {
        save: function () {
            var car = {id: 5, model: this.model, horsepower: this.horsepower, ownerId: this.ownerId};
            this.$http.post('http://localhost:8080/car', car).then(response => {
                response.status;
            });
            this.model = '';
            this.horsepower = '';
            this.ownerId = '';
        }
    }
});

Vue.component('cars-list', {
    props: ['cars'],
    data: function () {
        return {
            car: null
        }
    },
    template:
        '<div align="center" style="width: 300px;">' +
        '<car-form :cars="cars" :carAttr="car"/>' +
        '</div>',
});

var app = new Vue({
    el: '#app3',
    template: '<div align="center"><cars-list :cars="cars" /></div>',
    data: {
        cars: []
    }
});