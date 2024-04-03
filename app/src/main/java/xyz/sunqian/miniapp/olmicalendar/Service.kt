//package xyz.sunqian.miniapp.olmicalendar
//
//import android.content.Context
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.work.Data
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//
//var currentTime by remember { mutableStateOf(false) }
//
//class TimeWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
//
//    override fun doWork(): Result {
//        return Result.success(Data.Builder().putLong("time", System.currentTimeMillis()).build())
//    }
//}
//
//fun startTick() {
//    Thread {
//        while (true) {
//            currentTime = System.currentTimeMillis();
//        }
//    }.start()
//}