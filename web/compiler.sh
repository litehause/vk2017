cd ../
sbt web/clean web/compile web/xitrum-package

echo "./target/xitrum/script/runner ru.vk.web.Boot"