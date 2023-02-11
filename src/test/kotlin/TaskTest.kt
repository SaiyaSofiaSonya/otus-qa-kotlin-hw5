import io.kotest.core.spec.style.FeatureSpec
import okhttp3.Response
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.otus.TaskHttpClient
import ru.otus.dto.Priority
import ru.otus.dto.Task

class TaskTest: FeatureSpec({

    feature("A todo app") {
        val taskApi = TaskHttpClient()
        val testTask = Task(id = null, name = "test2", priority = Priority.LOW, completed = null)
        val testTaskCompleted = Task(id = null, name = "test2", priority = Priority.LOW, completed = false)

        scenario("Add task") {
            val task = taskApi.createTask(testTask)

            task?.id shouldNotBe null
            task?.priority shouldBe testTask.priority
        }

        scenario("Add task") {
            val task = taskApi.createTask(testTask)

            task?.id shouldNotBe null
            task?.priority shouldBe testTask.priority
        }

        scenario("Delete task") {
            val task = taskApi.createTask(testTask)
            task?.id?.let { taskApi.deleteTask(it, task) }
            val tasks = task?.let { taskApi.getTasks(it, false) }

            if (tasks != null) {
                tasks shouldNotContain  task
            }
        }

        scenario("Complete task") {
            val task = taskApi.createTask(testTask)
            task?.id?.let { taskApi.changeTask(it, task,true) }
            val tasks = task?.let { taskApi.getTasks(it, true) }

            tasks?.first() {it.id == task.id}?.completed shouldBe true
        }

        scenario("Uncompleted task") {
            val task = taskApi.createTask(testTask)
            task?.id?.let { taskApi.changeTask(it, task,false) }
            val tasks = task?.let { taskApi.getTasks(it, false) }

            tasks?.first() {it.id == task.id}?.completed shouldBe false
        }

        scenario("Delete if the id is invalid") {
           val rs: Response? = taskApi?.deleteTaskError(-404, testTask)

            rs?.code shouldBe 400
            rs?.message shouldBe "Wrong task ID!"
        }

        scenario("Delete if the id is not existed") {
            val rs: Response? = taskApi?.deleteTaskError(404, testTask)

            rs?.code shouldBe 404
            rs?.message shouldBe "Task not found"
        }

        scenario("Add a completed task") {
            val rs = taskApi?.createTaskError(testTaskCompleted)

            rs?.code shouldBe 400
            rs?.message shouldBe "Adding a completed task not allowed!"
        }


    }
})
