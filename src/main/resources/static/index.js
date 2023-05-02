let acmodel = ['原地换重心', '时间步', '方步', '锁步', '臂下转'];
let action = [0, 1, 2, 1, 2, 4, 3];

const form = document.querySelector('.data');

const button = document.querySelector('.button');
const name = document.querySelector('#name');
const id = document.querySelector('#id');
const footer = document.querySelector('.footer');

let totals = 0;

button.addEventListener('click', (e) => {
    if (name.value && id.value) {
        search(0, id.value);
        footer.style.display = 'block';
    } else {
        alert('请输入')
    }
})

function search(page, id) {
    console.log("开始查询");
    axios({
        method: 'get',
        url: '/api/data',
        params: {
            page: page,
            studentId: id,
        }
    }).then((res) => {
        form.innerHTML = "";
        let actions = res.data.data.list;
        console.log(actions);
        for (let i = 0; i < actions.length; i++) {
            let action = actions[i].actions;
            const number = document.createElement('h4');
            const div = document.createElement('div');
            number.textContent = `这是当页第${i + 1}条数据`;
            form.appendChild(number);
            action.map((item, index) => {
                // console.log(acmodel[index]);
                const span = document.createElement('span');
                span.textContent = acmodel[item];
                // console.log(span);
                div.appendChild(span);
                /* if (index < action.length - 1) {
                    const i = document.createElement('i');
                    i.textContent = ' | ';
                    form.appendChild(i);
                } */
            });
            form.appendChild(div);
        }
        totals = res.data.data.count;
        var p = new Pagination({
            container: '.page',
            size: 10,
            pageNo: page + 1,
            total: totals
        })
    }).catch((err) => {
        console.log(err);
    })
}

function Pagination(obj) {
    this.init(obj)
}
Pagination.prototype = {
    pages: {
        pageCount: 0,//页面总数
        size: 10,//单页面数据量
        pageNo: 1//当前页
    },
    //初始化页面数据,参数:obj
    init: function (obj) {
        var pages = this.pages
        pages.total = obj.total//总数据量
        obj.pageCount = Math.ceil(obj.total / obj.size)//总页码数
        pages.container = obj.container//外部容器
        pages.pageNo = obj.pageNo//当前页
        pages.pageCount = obj.pageCount//总页码数
        pages.eleHtml = obj.eleHtml//标签内部值
        pages.prevPage = obj.prevPage || 'prevPage'//上一页按钮
        pages.nextPage = obj.nextPage || 'nextPage'//下一页按钮
        this.renderPage(pages)
    },
    //构建页面,参数:args
    renderPage(args) {
        var pageContainer = this.selectEle(args.container)
        var pageStr = '', start, end
        //构建左侧点击按钮
        if (args.pageNo > 1) {
            pageStr = `<a class="prevPage">上一页</a>`
        } else {
            pageStr = `<a class="disabled">上一页</a>`
        }
        //构建中间页面按钮区域
        if (args.pageCount < 6) {//当总页码数小于6时
            for (start = 0; start < args.pageCount; start++) {
                end = start + 1
                if (end == args.pageNo) {
                    pageStr += '<a class="current">' + end + '</a>'
                } else {
                    pageStr += '<a>' + end + '</a>'
                }
            }
        } else {//当总页码大于等于6时
            start = args.pageNo - 1//确认遍历的起始位置为当前页的前一页
            end = args.pageNo + 1//确认遍历的结束位置为当前页的后一页
            if (args.pageNo > 2) { pageStr += '<a>' + 1 + '</a>' }//当前页大于2时，将页面1按钮写死
            else { end = 4 }//当前页小于等于2时，将遍历的结束位置写死为4
            if (args.pageNo > args.pageCount - 3) { start = args.pageCount - 3 }//当前页为最后四个页面时，将遍历的起始位置写死为倒数第四个页面值
            if (args.pageNo > 3) { pageStr += '<a>...</a>' }//当前页大于第三个页面时，将省略号按钮展现出来
            //对中间按钮进行遍历
            for (; start <= end; start++) {
                if (start <= args.pageCount && start > 0) {
                    if (start == args.pageNo) {
                        pageStr += '<a class="current">' + start + '</a>'
                    } else {
                        pageStr += '<a>' + start + '</a>'
                    }
                }
            }
            if (args.pageNo < args.pageCount - 2) { pageStr += '<a>...</a>' }//当前页面小于倒数第三个页面时，将省略号按钮展现出来
            if (args.pageNo < args.pageCount - 1) { pageStr += '<a>' + args.pageCount + '</a>' }//当前页面小于倒数第二个页面，将最后的页面按钮锁死
        }
        //构建右侧按钮
        if (args.pageNo < args.pageCount) {
            pageStr += `<a class="nextPage">下一页</a>`
        } else {
            pageStr += `<a class="disabled">下一页</a>`
        }
        pageContainer.innerHTML = pageStr
        this.switchPage()
    },
    //切换页面
    switchPage() {
        var pages = this.pages, g = this
        var aList = this.selectEle(pages.container + " a", true)//获取所有的a标签
        var current//定义一个当前页的标识
        //对所有的a标签遍历，绑定点击事件
        for (i in aList) {
            if (i < aList.length) {
                aList[i].addEventListener("click", function () {
                    var eleHtml = this.innerHTML//定义一个属性值来获取数字按钮
                    if (this.className == pages.prevPage) {
                        pages.pageNo > 1 && (pageNo = pages.pageNo - 1)
                    } else if (this.className == pages.nextPage) {
                        pages.pageNo < pages.pageCount && (pageNo = pages.pageNo + 1)
                    } else {
                        pageNo = parseInt(eleHtml)
                    }
                    // console.log(pageNo);
                    form.innerHTML = "";
                    search(pageNo - 1, id.value);
                    pageNo && g.gotoPage(pageNo)
                })
            }
        }
    },
    //跳转页面,参数:current
    gotoPage(current) {
        this.pages.pageNo = current
        this.renderPage(this.pages)
    },
    //获取页面元素
    selectEle(select, all) {
        return all ? document.querySelectorAll(select) : document.querySelector(select)
    }
}
