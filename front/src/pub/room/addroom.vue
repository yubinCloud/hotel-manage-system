<template>
    <div id="roomAdd">
        <el-dialog :title="title" :visible.sync="visible" top="0.5rem" :lock-scroll="false" :show-close="false"
                   :close-on-click-modal="false">
            <!-- 在el-form中加入属性 rules 可以实现对表单的参数认证 -->
            <el-form ref="roomForm" :model="newRoom" label-width="120px">
                <el-form-item label="房号：" prop="number">
                    <el-input v-model="newRoom.roomId" placeholder="请输入房号" clearable/>
                </el-form-item>
                <el-form-item label="房间名：" prop="roomName">
                    <el-input v-model="newRoom.roomName" placeholder="请输入房间名" clearable/>
                </el-form-item>
                <el-form-item label="客房类型：" prop="roomType">
                    <el-select v-model="newRoom.roomTypeId" placeholder="请选择客房类型" clearable @change="selectChanged">
                        <el-option
                            v-for="aRoomType in roomTypeList"
                            :key="aRoomType.id"
                            :label="aRoomType.name"
                            :value="aRoomType.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="客房状态：" prop="status">
                    <el-select v-model="newRoom.status" placeholder="请选择客房状态" clearable>
                        <el-option key="空闲" label="空闲" value="空闲"></el-option>
                        <el-option key="在住" label="在住" value="在住"></el-option>
                        <el-option key="正在打扫" label="正在打扫" value="正在打扫"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="楼层：" prop="floor">
                    <el-input v-model="newRoom.floor" placeholder="请输入楼层" clearable/>
                </el-form-item>
                <el-form-item label="床型：" prop="bedType">
                    <el-select v-model="newRoom.bedTypeId" placeholder="请选择床型" clearable>
                        <el-option
                            v-for="bedType in bedTypeList"
                            :key="bedType.id"
                            :label="bedType.bedName"
                            :value="bedType.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="房间设施说明：" prop="facilities">
                    <el-input type="textarea" :rows="2" v-model="newRoom.desc" resize="none" maxlength="200"
                              show-word-limit palceholder="房间设施说明"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer">
      <el-button type="primary" @click="resetForm('roomForm')">取消</el-button>
      <el-button type="primary" @click="submitForm('roomForm')">提交</el-button>
    </span>
        </el-dialog>
    </div>
</template>

<script>
import { getRoomTypeList, getRoomTypeById } from '@/api/roomtype';
import { getroomById } from '@/api/room';
import { uploadFile } from '@/api/uploadFile';
import { getBedTypeList } from '@/api/bedType';

export default {
    props: {
        title: String,
        default: 'title'
    },
    data() {
        return {
            visible: false,
            roomTypeList: [], // 客房类型
            bedTypeList: [],// 保存床型
            fileList: [],
            newRoom: {
                roomId: '',
                roomName: '',
                status: '',
                roomTypeId: '',
                floor: '',
                bedTypeId: '',
                desc: ''
            },
            rules: {
                number: [{ required: true, message: '请输入', trigger: 'blur' }],
                roomName: [{ required: true, message: '请输入', trigger: 'blur' }],
                roomType: [{ required: true, message: '请输入', trigger: 'change' }],
                status: [{ required: true, message: '请输入', trigger: 'change' }],
                floor: [{ required: true, message: '请输入', trigger: 'blur' }],
                bedType: [{ required: true, message: '请选择', trigger: 'change' }],
            }
        };
    },
    mounted() {
        this.getBedTypeList();
        this.getAllRoomTypeList();
    },
    methods: {
        open(item) { // item就是roomData
            // console.log('查看详情的item', item);
            this.visible = true;
            if (item === null || item === undefined) {
                this.newRoom = {};
            } else {
                this.getroomById(item.roomId);
            }
        },
        getAllRoomTypeList() {
            getRoomTypeList().then(res => {
                if (res.data.code === 0) {
                    this.roomTypeList = res.data.data;
                }
            });
        },
        getBedTypeList() {
            getBedTypeList().then(resp => {
                if (resp.data.code === 0) {
                    this.bedTypeList = resp['data']['data'];
                }
            })
        },
        getroomById(id) {
            getroomById(id).then(res => {
                if (res.data.code === 0) {
                    this.newRoom = res.data.data;
                    let imgList = res.data.data.imgList;
                    this.fileList = imgList.map(item => ({ url: item }));
                }
            });
        },
        selectChanged(value) {
            console.log('客房类型是切换', value);
            getRoomTypeById(value).then(res => {
                console.log('客房类型id查询到的数据是切换', res.data.data);
                if (res.data.code === 0) {
                    this.newRoom = res.data.data;
                    this.newRoom.roomTypeId = value;
                }
            });
        },
        formateDate(date) {
            let theDate = new Date(date);
            let year = theDate.getFullYear();
            let month = theDate.getMonth() + 1;
            let day = theDate.getDate();
            let hour = theDate.getHours();
            let minute = theDate.getMinutes();
            let second = theDate.getSeconds();
            return year + '-' + this.formatTen(month) + '-' + this.formatTen(day) + ' ' + this.formatTen(hour) + ':' + this.formatTen(minute) + ':' + this.formatTen(second);
        },
        formatTen(num) {
            return num > 9 ? (num + '') : ('0' + num);
        },
        beforeUpload(file) {
            console.log('file信息是', file);
            let files = new FormData();
            files.append('multipartFile', file);
            let headers = { 'Content-Type': 'multipart/form-data' };
            uploadFile(files, headers).then((res) => {
                console.log('文件上传返回数据', res.data.data);
                if (res.data.code === 0) {
                    let fileChild = {
                        // name: file.name,
                        url: res.data.data
                    };
                    this.fileList.push(fileChild);
                }
            });
            // console.log('this.fileList',this.fileList)
        },
        handleRemove(file, fileList) {
            // console.log(file, fileList);
            this.fileList = this.fileList.filter((val) => {
                (val.url).indexOf(file.url) < 0;
            });
        },
        handlePreview(file) {
            console.log(file);
        },
        submitForm(roomForm) {
            this.$refs.roomForm.validate(valid => {
                if (valid) {
                    this.$confirm('确认保存吗？', '是否保存', {
                        cancelButtonText: '取消',
                        confirmButtonText: '确认',
                        lockScroll: false,
                        type: 'warning'
                    }).then(() => {
                        this.$emit('confirmData', this.newRoom);
                        this.resetForm('roomForm');
                    });
                }
            });
        },
        resetForm(roomForm) {
            this.$nextTick(() => {
                this.$refs.roomForm.clearValidate();
            });
            this.newRoom = {};
            this.fileFlag = false;
            this.fileUploadPercent = 0;
            this.visible = false;
        }
    }
};
</script>

<style lang="scss">

</style>
