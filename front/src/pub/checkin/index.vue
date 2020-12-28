<template>
    <div id="employee">
        <el-form v-model="searchForm" :inline="true">
            <el-row>

                <el-col :span="6">
                    <el-form-item label="房号：" prop="number">
                        <el-input v-model="searchForm.roomId" placeholder="请输入房号" clearable/>
                    </el-form-item>
                </el-col>

                <el-col :span="8">
                    <el-form-item label="时间：">
                        <el-date-picker
                            v-model="createTimeRange"
                            type="daterange"
                            range-separator="-"
                            start-placeholder="入住时间"
                            end-placeholder="退房时间">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="4">
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" @click="onSearch(searchForm)">查询</el-button>
                        <el-button type="primary" icon="el-icon-plus" @click="$refs.addDialog.open(null)">新增</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <el-table
            border
            height="380"
            :data="checkinList"
            v-loading="loading"
            element-loading-text="拼命加载中"
            @cell-mouse-enter="mouseEnter"
        >
            <el-table-column label="序号" type="index" width="55">
                <template slot-scope="scope">
                    <!-- (当前页 - 1) * 当前显示数据条数 + 当前行数据的索引 + 1  -->
                    <span>{{ (page.currentPage - 1) * page.pageSize + scope.$index + 1 }}</span>
                </template>
            </el-table-column>
            <el-table-column label="房号" prop="roomId" />
            <el-table-column label="房间名" prop="room.name" />
            <el-table-column label="入住人" prop="guest.name" />
            <el-table-column label="入住人联系方式" prop="guest.phone" />
            <el-table-column label="入住时间" prop="checkInTime" />
            <el-table-column label="退房时间" prop="checkOutTime" />
            <el-table-column label="实际付款" prop="realPay" />
            <template>
                <el-button
                    type="text"
                    icon="el-icon-edit"
                    @click="$refs.updateDetails.open(checkinData)"
                >查看详情
                </el-button>
                <el-button
                    type="text"
                    icon="el-icon-edit"
                    class="red"
                    @click="$refs.updateRoom.open(checkinData)"
                >换房
                </el-button>
                <el-button
                    type="text"
                    icon="el-icon-edit"
                    class="red"
                    @click="$refs.updateDialog.open(checkinData)"
                >修改
                </el-button>
                <el-button
                    type="text"
                    icon="el-icon-edit"
                    class="red"
                    @click="$refs.checkOut.open()"
                >退房
                </el-button>
            </template>
        </el-table>
        <add-dialog ref="addDialog" title="新增入住" @confirmData="(item) => addcheckin(item)"/>
        <check-out ref="checkOut" title="调整房费" @confirmData="(item) => checkout(item)"/>
        <update-details ref="updateDetails" title="入住详情"/>
        <update-dialog ref="updateDialog" title="修改入住天数" @confirmData="(item) => updateCheckin(item)"/>
        <update-room ref="updateRoom" title="换房" @confirmData="(item) => updateRoom(item)"/>
        <page-component :total="page.totalSize" :page="page" @pageChange="(item)=>handlePageChange(item)"/>
    </div>
</template>

<script>
import AddDialog from './addCheckin';
import UpdateDetails from './details';
import UpdateDialog from './updateDialog';
import UpdateRoom from './updateroom';
import CheckOut from './checkout';
import { getCheckinList, addCheckin, updateCheckin, updateRoom } from '@/api/checkin';
import { addCheckout } from '@/api/checkout';
import { getCategoryList } from '@/api/category';
import PageComponent from '@/components/Pagenation/index';
import { formateDate } from '@/utils/formateDate';

export default {
    components: {
        PageComponent,
        AddDialog,
        UpdateDetails,
        UpdateDialog,
        UpdateRoom,
        CheckOut
    },
    data() {
        return {
            loading: true,
            searchForm: {},
            states: [
                {
                    label: '未退房未到期',
                    value: 1
                },
                {
                    label: '未退房已到期',
                    value: 2
                }
            ],
            createTimeRange: '',
            checkinList: [],
            checkinData: {},
            bedTypeList: [],
            page: {
                currentPage: 0, // 当前页，对应接口中的page
                pageSize: 0, // 每页条数，对应接口中的limit
                totalSize: 0, // 中条数，对应接口中的res.data.page.totalRows
                totalPage: 0 // 总页数，对应接口中的res.data.page.totalPages
            }
        };
    },
    mounted() {
        this.getCheckinList(null);
        this.getCategoryList();
    },
    methods: {
        formateDate,
        handlePageChange(item) {
            const para = { page: item.currentPage, limit: item.pageSize };
            this.getCheckinList(para);
        },
        onSearch(params) {
            if (this.createTimeRange == null || this.createTimeRange === '') {
                params.startTime = null;
                params.endTime = null;
            } else {
                params.startTime = this.formateDate(this.createTimeRange[0]);
                params.endTime = this.formateDate(this.createTimeRange[1]);
            }
            getCheckinList(params).then(resp => {
                if (resp.data.code === 0) {
                    this.checkinList = resp.data.data;
                    this.loading = false;
                } else if (resp.data.code === 3) {
                    alert('登录已过期，请重新登录');
                    this.$router.push({ path: '/login' });
                }
            });
        },
        getCheckinList(param) {
            getCheckinList(param).then(res => {
                if (res.data.code === 0) {
                    // console.log('返回的数据是',res.data)
                    this.checkinList = res.data.data;
                    this.loading = false;
                } else if (res.data.code === 3) {
                    alert('登录已过期，请重新登录');
                    this.$router.push({ path: '/login' });
                }
            });
        },
        mouseEnter(data) {
            this.checkinData = Object.assign({}, data);
        },
        addcheckin(item) { // 新增的住客不止一个，可能有多个人，这种写法不行
            console.log('新增入住通知的参数是', item);
            let tenantsArr = item.moreNotifyObject;
            let tenantsArrChild = {
                'tenantIdCard': item.tenantIdCard,
                'tenantName': item.tenantName,
                'tenantSex': item.tenantSex,
                'tenantTel': item.tenantTel
            };
            tenantsArr.push(tenantsArrChild);
            const param = {
                'checkinDay': Number(item.checkinDay),
                'errorInfo': {},
                'roomId': item.roomId,
                'tenants': tenantsArr
            };
            console.log('param', param);
            const headers = { 'Content-type': 'application/json;charset=utf-8' };
            addCheckin(JSON.stringify(param), headers).then(res => {
                // console.log('新增入住',res)
                if (res.data.code == 5) {
                    this.$message({
                        type: 'info',
                        message: res.data.data
                    });
                } else if (res.data.code === 0) {
                    this.$message({
                        type: 'success',
                        message: '新增入住成功'
                    });
                    this.getCheckinList();
                }
            });
        },
        updateCheckin(item) {
            const param = {
                checkinId: item.checkinId,
                checkinDay: item.checkinDay
            };
            // console.log('修改入住天数的参数是',param)
            updateCheckin(param).then(res => {
                // console.log('修改入住天数的参数是',res.data)
                if (res.data.code == 0) {
                    this.$message({
                        type: 'success',
                        message: '修改入住天数成功'
                    });
                } else if (res.data.code === 7) {
                    this.$message({
                        type: 'info',
                        message: res.data.data
                    });
                }
                this.getCheckinList();
            });
        },
        updateRoom(item) {
            const param = {
                checkinId: item.checkinId,
                roomId: item.roomId
            };
            console.log('换房的参数是', param);
            updateRoom(param).then(res => {
                console.log('换房的返回的数据是', res.data);
                if (res.data.code == 0) {
                    this.$message({
                        type: 'success',
                        message: '换房成功'
                    });
                }
                this.getCheckinList();
            });
        },
        checkout(item) {
            const param = {
                checkinId: this.checkinData.checkinId,
                money: item.money
            };
            addCheckout(param).then(res => {
                console.log('退房的返回的数据是', res.data);
                if (res.data.code == 0) {
                    this.$message({
                        type: 'success',
                        message: '退房成功'
                    });
                } else if (res.data.code == 5) {
                    this.$message({
                        type: 'info',
                        message: res.data.data
                    });
                }
            });
        }
    }
};
</script>

<style lang="less">

</style>
