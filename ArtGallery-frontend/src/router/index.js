import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Home from '@/components/Home.vue'
import CustomerLogin from '@/components/CustomerLogin'
import ArtistLogin from '@/components/ArtistLogin'
import ManagerLogin from '@/components/ManagerLogin'
import CreateCustomer from '@/components/CreateCustomer'
import CreateArtist from '@/components/CreateArtist'
import CreateManager from '@/components/CreateManager'
import CustomerPage from '@/components/CustomerPage'
import ArtistPage from '@/components/ArtistPage'
import ManagerPage from '@/components/ManagerPage'
import CartPage from '@/components/CartPage'
import CheckoutPage from '@/components/CheckoutPage'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/home/customerlogin',
      name: 'CustomerLogin',
      component: CustomerLogin
    },
    {
      path: '/home/artistlogin',
      name: 'ArtistLogin',
      component: ArtistLogin
    },
    {
      path: '/home/managerlogin',
      name: 'ManagerLogin',
      component: ManagerLogin
    },
    {
      path: '/home/createCustomer',
      name: 'CreateCustomer',
      component: CreateCustomer
    },
    {
      path: '/home/createArtist',
      name: 'CreateArtist',
      component: CreateArtist
    },
    {
      path: '/home/createManager',
      name: 'CreateManager',
      component: CreateManager
    }, 
    {
      path: '/home/customerpage',
      name: 'CustomerPage',
      component: CustomerPage
    },
    {
      path: '/home/artistpage',
      name: 'ArtistPage',
      component: ArtistPage
    },
    {
      path: '/home/managerpage',
      name: 'ManagerPage',
      component: ManagerPage
    },
    {
      path: '/home/cartpage',
      name: 'CartPage',
      component: CartPage
    },
    {
      path: '/home/checkoutpage',
      name: 'CheckoutPage',
      component: CheckoutPage
    },
  ]
})
