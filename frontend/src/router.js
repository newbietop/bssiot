
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import CustomerCustomerManager from "./components/listers/CustomerCustomerCards"
import CustomerCustomerDetail from "./components/listers/CustomerCustomerDetail"

import RateRaterManager from "./components/listers/RateRaterCards"
import RateRaterDetail from "./components/listers/RateRaterDetail"

import BillingBillingManager from "./components/listers/BillingBillingCards"
import BillingBillingDetail from "./components/listers/BillingBillingDetail"

import SettlementSettlementManager from "./components/listers/SettlementSettlementCards"
import SettlementSettlementDetail from "./components/listers/SettlementSettlementDetail"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/customers/customers',
                name: 'CustomerCustomerManager',
                component: CustomerCustomerManager
            },
            {
                path: '/customers/customers/:id',
                name: 'CustomerCustomerDetail',
                component: CustomerCustomerDetail
            },

            {
                path: '/rates/raters',
                name: 'RateRaterManager',
                component: RateRaterManager
            },
            {
                path: '/rates/raters/:id',
                name: 'RateRaterDetail',
                component: RateRaterDetail
            },

            {
                path: '/billings/billings',
                name: 'BillingBillingManager',
                component: BillingBillingManager
            },
            {
                path: '/billings/billings/:id',
                name: 'BillingBillingDetail',
                component: BillingBillingDetail
            },

            {
                path: '/settlements/settlements',
                name: 'SettlementSettlementManager',
                component: SettlementSettlementManager
            },
            {
                path: '/settlements/settlements/:id',
                name: 'SettlementSettlementDetail',
                component: SettlementSettlementDetail
            },



    ]
})
