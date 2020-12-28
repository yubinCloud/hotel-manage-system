<template>
    <div id="roomstate">
        <el-form v-model="searchForm" :inline="true">
            <el-row>
                <el-col :span="6">
                    <el-form-item label="房号：">
                        <el-input v-model="searchForm.roomId" placeholder="请输入房号" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="客房类型：" prop="roomType">
                        <el-select v-model="searchForm.roomTypeId" placeholder="请选择客房类型" clearable>
                            <el-option
                                v-for = "rt in roomTypeList"
                                :key = "rt.id"
                                :label = "rt.name"
                                :value = "rt.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="可住人数：">
                        <el-input v-model="searchForm.peopleNum" placeholder="请输入可住人数" clearable/>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="楼层：">
                        <el-input v-model="searchForm.floor" placeholder="请输入楼层" clearable/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="6">
                    <el-form-item label="床型：">
                        <el-select v-model="searchForm.bedTypeId" placeholder="请选择床型" clearable>
                            <el-option
                                v-for="bedType in bedTypeList"
                                :key="bedType.id"
                                :label="bedType.bedName"
                                :value="bedType.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="客房状态：">
                        <el-select v-model="searchForm.status" placeholder="请选择客房状态" clearable>
                            <el-option key="空闲" label="空闲" value="空闲"></el-option>
                            <el-option key="在住" label="在住" value="在住"></el-option>
                            <el-option key="不可用" label="不可用" value="不可用"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="房费：" prop="startMoney">
                        <el-input v-model="searchForm.startPrice" placeholder="最低房费" clearable style="width:48%"/>
                        -
                        <el-input v-model="searchForm.endPrice" placeholder="最高房费" clearable style="width:48%"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-button type="primary" icon="el-icon-search" @click="getRoomList(searchForm)">查询</el-button>
            <el-button type="primary" icon="el-icon-plus" @click="$refs.addDialog.open(null)">新增</el-button>
        </el-form>

        <!-- 客房显示表格 -->
        <el-table
            border
            height="300"
            :data="roomList"
            v-loading="loading"
            @cell-mouse-enter="mouseEnter"
        >
            <el-table-column label="序号" type="index" width="55">
                <template slot-scope="scope">
                    <!-- (当前页 - 1) * 当前显示数据条数 + 当前行数据的索引 + 1  -->
                    <span>{{ (page.currentPage - 1) * page.pageSize + scope.$index + 1 }}</span>
                </template>
            </el-table-column>
            <el-table-column label="房号" prop="id" />
            <el-table-column label="房名" prop="name" />
            <el-table-column label="房间类型" prop="roomType.name" />
            <el-table-column label="楼层" prop="floor" />
            <el-table-column label="面积" prop="roomType.area" />
            <el-table-column label="可住人数" prop="roomType.peopleNum" />
            <el-table-column label="床型" prop="bedType.bedName" />
            <el-table-column label="标准价" prop="roomType.stdPrice" />
            <el-table-column label="客房状态" prop="status" />
            <el-table-column label="房间描述" prop="desc" />
                <template>
                    <el-button
                        type="text"
                        icon="el-icon-edit"
                        @click="$refs.updateDialog.open(roomData)"
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

            <el-drawer
                title="客房图片详情"
                :visible.sync="drawer"
                :append-to-body='true'
                direction="rtl"
                size="50%">
                <div v-for="(img, index) in roomData.imgList" :key="index">
                    <img :src="img" width="400" alt="图片暂时无法找到">
                </div>
            </el-drawer>
        </el-table>


        <add-dialog ref="addDialog" title="新增" @confirmData="(item) => addroom(item)"/>
        <update-dialog ref="updateDialog" title="修改" @confirmData="(item,fileList) => updateroom(item,fileList)"/>
        <page-component :total="page.totalSize" :page="page" @pageChange="(item)=>handlePageChange(item)"/>
    </div>
</template>

<script>
import AddDialog from './addroom'
import updateDialog from './addroom'
import {getroomList, addRoom, updateroom, delroom} from '@/api/room';
import {getRoomTypeList} from '@/api/roomtype'
import {getBedTypeList} from '@/api/bedType'
import PageComponent from '@/components/Pagenation/index'

export default {
    components: {
        PageComponent,
        AddDialog,
        updateDialog
    },
    data() {
        return {
            loading: true,
            drawer: false, // 控制抽屉的显影
            searchForm: {
            },
            roomList: [],
            bedTypeList: [],
            roomTypeList: [], // 客房类型
            roomData: '', // 保存该条数据的roomId
            page: {
                currentPage: 0, // 当前页，对应接口中的page
                pageSize: 0, // 每页条数，对应接口中的limit
                totalSize: 0, // 中条数，对应接口中的res.data.page.totalRows
                totalPage: 0 // 总页数，对应接口中的res.data.page.totalPages
            }
        }
    },
    mounted() {
        this.getRoomList(null);
        this.getAllRoomTypeList()
        this.getBedTypeList()
    },
    methods: {
        mouseEnter(data) {
            this.roomData = Object.assign({}, data)
        },

        handlePageChange(item) {
            // console.log(item);// currentPage=1  pageSize=30条
            const para = {page: item.currentPage, limit: item.pageSize};
            this.getroomList(para);
        },

        getBedTypeList() {
            getBedTypeList().then(resp => {
                if (resp.data.code === 0) {
                    this.bedTypeList = resp['data']['data'];
                }
            })
        },

        getRoomList(param) {
            getroomList(param).then(res => {
                if (res['data']['code'] === 0) {
                    // this.page.currentPage = res.data.page.page;
                    // this.page.pageSize = res.data.page.limit;
                    // this.page.totalPage = res.data.page.totalPages;
                    // this.page.totalSize = res.data.page.totalRows;
                    // console.log('返回的客房类型数据是',res.data)
                    this.roomList = res['data']['data'];
                    this.loading = false;
                } else if (res.data.code === 3) {
                    alert('登录已过期，请重新登录')
                    this.$router.push({path: '/login'});
                }
            })
        },
        getAllRoomTypeList() {
            getRoomTypeList(null).then(resp => {
                if (resp['data']['code'] === 0) {
                    this.roomTypeList = resp['data']['data'];
                }
            })
        },
        addroom(newRoom) {
            // console.log('新增客房的是', item)
            addRoom(newRoom).then(resp => {
                // console.log('新增客房返回数据是', resp)
                if (resp.data.code === 0) {
                    this.$message({
                        type: 'success',
                        message: '新增客房成功'
                    })
                    this.getroomList()
                }
                if (resp.data.code === 5) {
                    this.$message({
                        type: 'info',
                        message: resp.data.data
                    })
                }
            })
        },
        updateroom(item, fileList) { // 修改客房类型
            // console.log('修改客房参数是', item)
            let imgUrls = []
            fileList.forEach((ele, idx) => {
                imgUrls.push(ele.url)
            })
            item.imgUrls = imgUrls.join()
            delete item.imgList
            // console.log('修改客房参数是', item)
            updateroom(item).then(res => {
                // console.log('修改客房类型返回数据是', res)
                if (res.data.code === 0) {
                    this.$message({
                        type: 'success',
                        message: '修改客房详情成功'
                    })
                    this.getroomList()
                }
            })
        },
        handleDelete() {
            this.$confirm('此操作将永久删除该数据，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then((res) => {
                delroom(this.roomData.roomId).then(res => {
                    if (res.data.code === 0) {
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        })
                        this.getroomList()
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                })
            })
        }
    }
}
</script>

<style lang="less">
.el-drawer__body {
    overflow: auto;
}

#room {
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
