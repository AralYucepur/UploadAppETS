Projede syntaxına daha çok hakim olduğum için Gradle üzerinden ilerledim. App'in ileride geliştirilme olasılığı 
olmasından dolayı microservice yapısında düzenledim. Config service içinde yml bilgileri mevcut. Önce Config service
sonra FileService çalıştırılmalı. App http://localhost:9090/swagger-ui/index.html#/ üzerinden çalışmakta.
App çalıştırılmadan önce upload edilecek dosyanın pathi kullanıcı tarafından düzenlenmeli (FileService:42).
Localda çalıştığı için önemli veriler gizlenmemiş durumda. Bütün end pointler çalışır durumda. JWT güvenliğini kurulumu 
yapıldı fakat kullanıcı sistemi olmadığı için etkinliği yok. Kullanıcı sistemi eklenmesi durumunda hata sistemi mevcut.