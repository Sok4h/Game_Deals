 # Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

 -dontwarn com.android.org.conscrypt.SSLParametersImpl
 -dontwarn org.apache.harmony.xnet.provider.jsse.SSLParametersImpl
 -dontwarn org.bouncycastle.jsse.BCSSLParameters
 -dontwarn org.bouncycastle.jsse.BCSSLSocket
 -dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
 -dontwarn org.openjsse.javax.net.ssl.SSLParameters
 -dontwarn org.openjsse.javax.net.ssl.SSLSocket
 -dontwarn org.openjsse.net.ssl.OpenJSSE
