cd ../
sbt admin/clean admin/compile admin/xitrum-package

echo "./target/xitrum/script/runner ru.vk.admin.Boot"