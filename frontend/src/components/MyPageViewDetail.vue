<template>

    <v-card outlined>
        <v-card-title>
            MyPage # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="CustomerId" v-model="item.customerId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="ProductCd" v-model="item.productCd" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="SvccontStatus" v-model="item.svccontStatus" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="SvcContNo" v-model="item.svcContNo" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Email" v-model="item.email" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Address" v-model="item.address" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="ProductNm" v-model="item.productNm" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'MyPageViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/myPages/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

