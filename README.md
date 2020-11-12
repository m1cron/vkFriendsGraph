# vkFriendsGraph
### Java Junior task

`Входные данные:` <br/>
<pre>
Текстовый файл, подается как параметр main метода,
Первый ID VK исследуемый, остальные - с кем.
</pre>

`Результат:` <br/>
`````
Файл-CSV:
            <ID 1>
            <ID 2>
            <расстояние (0 - нет связи, 1 - прямая связь, 2 и более - кол-во ребер, через друзей-друзей)
            -1 - неизвестно)>

Пример:
        1,2,0
        1,3,1
        1,4,2
`````

`Требования:` <br/>
- Тестирование jUnit с максимальным покрытием кода
- Сохранить в БД (MySQL/PostgreSQL/Redis)
- Использовать VK API
- Собрать на Maven

`Видео с таском` <br/>
[![Видео](https://img.youtube.com/vi/wMRJy6RmdwU/0.jpg)](https://youtu.be/wMRJy6RmdwU)

`Использование:` <br/>
```
$ git clone https://github.com/m1cron/vkFriendsGraph
$ cd vkFriendsGraph
$ mvn package
$ java -jar vkFriendsGraph-1.0.jar [id's txt file] [out.csv] [search depth] [VK API token]
```
