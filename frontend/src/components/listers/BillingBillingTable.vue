<template>
    <div>
        <v-data-table
                :headers="headers"
                :items="values"
                :items-per-page="5"
                class="elevation-1"
        ></v-data-table>

        <v-col style="margin-bottom:40px;">
            <div class="text-center">
                <v-dialog
                        v-model="openDialog"
                        width="332.5"
                        fullscreen
                        hide-overlay
                        transition="dialog-bottom-transition"
                >
                    <template v-slot:activator="{ on, attrs }">
                        <v-fab-transition>
                            <v-btn
                                    color="primary"
                                    fab
                                    dark
                                    large
                                    style="position:absolute; bottom: 5%; right: 2%; z-index:99"
                                    @click="openDialog=true;"
                            >
                                <v-icon v-bind="attrs" v-on="on">mdi-plus</v-icon>
                            </v-btn>
                        </v-fab-transition>
                    </template>

                    <BillingBilling :offline="offline" class="video-card" :isNew="true" :editMode="true" v-model="newValue" @add="append" v-if="tick"/>
                
                    <v-btn
                            style="postition:absolute; top:2%; right:2%"
                            @click="closeDialog()"
                            depressed 
                            icon 
                            absolute
                    >
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </v-dialog>
            </div>
        </v-col>
    </div>
</template>

<script>
    const axios = require('axios').default;
    import BillingBilling from './../BillingBilling.vue';

    export default {
        name: 'BillingBillingManager',
        components: {
            BillingBilling,
        },
        props: {
            offline: Boolean,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            values: [],
            headers: 
                [
                    { text: "id", value: "id" },
                    { text: "chargeAccount", value: "chargeAccount" },
                    { text: "productCd", value: "productCd" },
                    { text: "productTarif", value: "productTarif" },
                    { text: "invoiceFileNm", value: "invoiceFileNm" },
                    { text: "invoiceFilePath", value: "invoiceFilePath" },
                    { text: "productNm", value: "productNm" },
                    { text: "chargeAmount", value: "chargeAmount" },
                    { text: "useAmount", value: "useAmount" },
                    { text: "svcContNo", value: "svcContNo" },
                ],
            billing : [],
            newValue: {},
            tick : true,
            openDialog : false,
        }),
        async created() {
            if(this.offline){
                if(!this.values) this.values = [];
                return;
            }

            var temp = await axios.get(axios.fixUrl('/billings'))
            temp.data._embedded.billings.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])
            this.values = temp.data._embedded.billings;

            this.newValue = {
                'chargeAccount': 0,
                'productCd': '',
                'productTarif': 0,
                'invoiceFileNm': '',
                'invoiceFilePath': '',
                'productNm': '',
                'chargeAmount': 0,
                'useAmount': 0,
                'svcContNo': 0,
            }
        },
        methods: {
            closeDialog(){
                this.openDialog = false
            },
            append(value){
                this.tick = false
                this.newValue = {}
                this.values.push(value)
                
                this.$emit('input', this.values);

                this.$nextTick(function(){
                    this.tick=true
                })
            },
        }
    }
</script>

