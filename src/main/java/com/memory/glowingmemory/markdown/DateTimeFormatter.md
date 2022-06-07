#DateTimeFormatter
```markdown
LocalDateTime 在数据库中对应的类型 timestamp
Date 在数据库中对应的类型 datetime

Date 和 SimpleDateFormat、Calendar都是线程不安全的
LocalDateTime 和 DateTimeFormatter 都是线程安全的
```

#格式化时间日期
```markdown
LocalDateTime dt = LocalDateTime.now(); 
String str = dt.format(DateTimeFormatter.ISO_DATE);
String str1 = dt.format(DateTimeFormatter.BASIC_ISO_DATE);
String str2 = dt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
String str3 = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//str 2020-06-27
//str1 20200627
//str2 2020/06/27
//str3 2020-06-27 11:11:11
// 减使用minus
dt.minusYears(1L);
// 加使用plus
dt.plusMonths(5);
// 直接修改使用with
dt.withYear(2022);

//当天最大时间 2020-06-27 23:59:59
dt.with(LocalTime.MAX);
//当天最小时间 2020-06-27 00:00:00
dt.with(LocalTime.MIN);

Month month = dt.getMonth();
System.out.println(month);//JUNE
System.out.println(month.getValue());//6
```

```markdown
DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
String time = dateTimeFormatter.format(LocalDateTime.now());
LocalDateTime parse = LocalDateTime.parse("2022-05-19 15:54:49", dateTimeFormatter);
String c_send_time = dateTimeFormatter.format(parse.plusHours(-8));
```

```markdown
    /**
     * 日期转为LocalDateTime
     *
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(final Date date) {
        if (null == date) {
            return null;
        }
        final Instant instant = date.toInstant();
        final ZoneId zoneId = ZoneId.systemDefault();
        final LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }
    /**
     * 日期转为LocalDate
     *
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDate dateToLocalDate(final Date date) {
        if (null == date) {
            return null;
        }
        final Instant instant = date.toInstant();
        final ZoneId zoneId = ZoneId.systemDefault();
        final LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }

    /**
     * LocalDateTime转为日期
     *
     * @param localDateTime LocalDateTime
     * @return 日期
     */
    public static Date localDateTimeToDate(final LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zdt = localDateTime.atZone(zoneId);
        final Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * LocalDate转为日期
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(final LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zdt = localDate.atStartOfDay().atZone(zoneId);
        final Date date = Date.from(zdt.toInstant());
        return date;
    }

```