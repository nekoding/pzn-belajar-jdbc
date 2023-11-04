## Latihan JDBC

Latihan menggunakan JDBC untuk berinteraksi dengan database. Ada sedikit perbedaan dengan kode yang terdapat dalam kursus PZN. Disini saya menggunakan `Postgresql` bukan `MySQL`

## Environment Config

```
DB_URL=
DB_USER=
DB_PASS=
```

## SQL Init

Create table customers
```roomsql
CREATE TABLE customers(
    id VARCHAR,
    name VARCHAR,
    email VARCHAR,
    CONSTRAINT email_unique UNIQUE(email)
)
```

Create table comments
```roomsql
CREATE TABLE comments (
    id VARCHAR default gen_random_uuid(),
    name VARCHAR,
    comment TEXT,
    primary key(id)
)
```