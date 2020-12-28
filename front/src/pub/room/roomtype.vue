<template>
    <div id="roomtype">
        <el-form v-model="searchForm" :inline="true">
            <el-row>
                <el-col :span="6">
                    <el-form-item label="客房类型：" prop="roomType">
                        <el-input v-model="searchForm.roomTypeName" placeholder="请输入客房类型" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="可住人数：">
                        <el-input v-model="searchForm.peopleNum" placeholder="请输入可住人数" clearable/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="10">
                    <el-form-item label="房费：" prop="moneySection">
                        <el-input v-model="searchForm.minPrice" placeholder="最低房费" clearable style="width:48%"/>
                        -
                        <el-input v-model="searchForm.maxPrice" placeholder="最高房费" clearable style="width:48%"/>
                    </el-form-item>
                </el-col>
                <el-col :span="10">
                    <el-form-item label="面积：" prop="areaSection">
                        <el-input v-model="searchForm.minArea" placeholder="最小面积" clearable style="width:48%"/>
                        -
                        <el-input v-model="searchForm.maxArea" placeholder="最大面积" clearable style="width:48%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="6">
                    <el-button type="primary" icon="el-icon-search" @click="getRoomTypeList(searchForm)">查询</el-button>
                    <el-button type="primary" icon="el-icon-plus" @click="$refs.addDialog.open(null)">新增</el-button>
                </el-col>
            </el-row>
        </el-form>
        <el-table
            border
            height="330"
            :data="roomTypeList"
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
            <el-table-column label="客房类型" prop="name" width="100"/>
            <el-table-column label="面积" prop="area"/>
            <el-table-column label="可住人数" prop="peopleNum"/>
            <el-table-column label="标准价" prop="stdPrice"/>
            <template>
                <el-button
                    type="text"
                    icon="el-icon-edit"
                    @click="$refs.updateDialog.open(roomTypeData)"
                >查看详情
                </el-button>
                <el-button
                    type="text"
                    style="margin-left: 16px;"
                    icon="el-icon-s-tools"
                    @click="drawer = true"
                >查看图片
                </el-button>
                <el-button
                    type="text"
                    icon="el-icon-delete"
                    class="red"
                    @click="handleDelete"
                >删除
                </el-button>
            </template>
        </el-table>

        <add-dialog ref="addDialog" title="新增" @confirmData="(item) => addroomtype(item)"/>
        <update-dialog ref="updateDialog" title="修改" @confirmData="(item,fileList) => updateroomtype(item,fileList)"/>
        <page-component :total="page.totalSize" :page="page" @pageChange="(item)=>handlePageChange(item)"/>
    </div>
</template>

<script>
import AddDialog from './addRoomType';
import updateDialog from './addRoomType';
import { getRoomTypeList, addRoomType, updateRoomType, delRoomType } from '@/api/roomtype';
import PageComponent from '@/components/Pagenation/index';

export default {
    components: {
        PageComponent,
        AddDialog,
        updateDialog
    },
    data() {
        return {
            loading: true,
            searchForm: {},
            roomTypeList: [],
            roomTypeData: {},
            page: {
                currentPage: 0, // 当前页，对应接口中的page
                pageSize: 0, // 每页条数，对应接口中的limit
                totalSize: 0, // 中条数，对应接口中的res.data.page.totalRows
                totalPage: 0 // 总页数，对应接口中的res.data.page.totalPages
            }
        };
    },
    mounted() {
        this.getRoomTypeList(null);
    },
    methods: {
        handlePageChange(item) {
            // console.log(item);// currentPage=1  pageSize=30条
            const para = { page: item.currentPage, limit: item.pageSize };
            this.getRoomTypeList(para);
        },

        getRoomTypeList(param) {
            getRoomTypeList(param).then(resp => {
                if (resp.data.code === 0) {
                    this.roomTypeList = resp['data']['data'];
                    this.loading = false;
                } else if (resp.data.code === 3) {
                    alert('登录已过期，请重新登录');
                    this.$router.push({ path: '/login' });
                }
            });
        },
        mouseEnter(data) { // 这个数据拿不到
            this.roomTypeData = Object.assign({}, data);
        },
        addroomtype(item, fileList) {
            addRoomType(item).then(res => {
                // console.log('新增客房返回数据是', res)
                if (res.data.code === 0) {
                    this.$message({
                        type: 'success',
                        message: '新增客房类型成功'
                    });
                    this.getRoomTypeList();
                }
                if (res.data.code === 5) {
                    this.$message({
                        type: 'info',
                        message: res.data.data
                    });
                }
            });
        },
        updateroomtype(item, fileList) { // 修改客房类型
            let imgUrls = [];
            fileList.forEach((ele, idx) => {
                imgUrls.push(ele.url);
            });
            item.imgUrls = imgUrls.join();
            delete item.imgList;
            // console.log('修改客房类型item', item)
            updateRoomType(item).then(res => {
                // console.log('修改客房类型返回数据是', res)
                if (res.data.code === 0) {
                    this.$message({
                        type: 'success',
                        message: '修改客房类型成功'
                    });
                    this.getRoomTypeList();
                }
            });
        },
        handleDelete() {
            this.$confirm('此操作将永久删除该数据，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then((res) => {
                delRoomType(this.roomTypeData.roomTypeId).then(res => {
                    if (res.data.code === 0) {
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                        this.getRoomTypeList();
                    }
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }
    }
};
</script>

<style lang="less">
#roomtype {
    // .el-row {
    //   margin-bottom: 10px;
    //   .el-col-12:first-child {
    //     padding-right: 10px;
    //   }
    //   .el-col-12:last-child{
    //     padding-left: 10px;
    //   }
    // }
    .el-card__body {
        display: flex;

        .contentBox {
            padding: 14px;

            div {
                margin-bottom: 10px;

                a {
                    margin: 0 15px 15px 0;
                }
            }
        }
    }
}
</style>
