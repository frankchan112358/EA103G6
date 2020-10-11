<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
$(document).ready(function()
{
    /*calendar */
    var todayDate = moment().startOf('day');
    var YM = todayDate.format('YYYY-MM');
    var YESTERDAY = todayDate.clone().subtract(1, 'day').format('YYYY-MM-DD');
    var TODAY = todayDate.format('YYYY-MM-DD');
    var TOMORROW = todayDate.clone().add(1, 'day').format('YYYY-MM-DD');


    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        plugins: ['dayGrid', 'list', 'timeGrid', 'interaction', 'bootstrap'],
        themeSystem: 'bootstrap',
        timeZone: 'UTC',
        //dateAlignment: "month", //week, month
        buttonText: {
            today: '今天',
            month: '月',
            week: '周',
            day: '天',
            list: '清單'
        },
        eventTimeFormat: {
            hour: 'numeric',
            minute: '2-digit',
            meridiem: 'short'
        },
        navLinks: true,
        header: {
            left: 'title',
            center: '',
            right: 'today prev,next'
        },
        footer: {
            left: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek',
            center: '',
            right: ''
        },
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        events: [{
                title: 'EA103 305 HTML 董董',
                start: YM + '-14T13:30:00',
                end: YM + '-14T17:00:00',
                className: "bg-danger border-danger"
            },
            {
                title: 'EA103 305 早上 Java 吳冠宏',
                start: YM + '-02T09:00:00',
                end: YM + '-02T12:00:00',
                className: "bg-primary text-white"
            },
            {
                title: 'EA103 305 下午 Java 吳冠宏',
                start: YM + '-08T13:30:00',
                end: YM + '-08T17:00:00',
                className: "bg-primary text-white"
            },
            {
                title: 'EA103 305 早上 Servlet 吳永誌',
                start: YM + '-10T09:00:00',
                end: YM + '-10T12:0:00',
                className: "bg-success border-success text-white"
            },
            {
                title: 'EA103 305 早上 Servlet 吳永誌',
                start: TODAY + 'T09:00:00',
                end: TODAY + 'T12:0:00',
                className: "bg-success border-success "
            },
            {
                title: 'Meeting',
                start: TODAY + 'T12:15:00',
                end: TODAY + 'T13:30:00',
                className: "bg-white text-info  border-info"
            },
            {
                title: 'EA103 305 下午 Ajax 戰神',
                start: TODAY + 'T14:00:00',
                end: TODAY + 'T17:30:00',
                className: "bg-warning text-white border-warning"
            },
        ],
        viewSkeletonRender: function() {
            $('.fc-toolbar .btn-default').addClass('btn-sm');
            $('.fc-header-toolbar h2').addClass('fs-md');
            $('#calendar').addClass('fc-reset-order')
        },

    });

    calendar.render();
});
</script>