package ru.nsu.databases.ui.splash_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.databases.R
import ru.nsu.databases.databinding.FragmentSplashBinding
import ru.nsu.databases.ui.base.BaseFragment
import ru.nsu.databases.ui.base.viewBinding
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import java.util.Locale
import java.util.TimeZone


@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    override val binding by viewBinding { inflater, container ->
        FragmentSplashBinding.inflate(inflater, container, false)
    }

    private val viewModel: SplashFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVmObservers()

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver")
        } catch (e1: ClassNotFoundException) {
            e1.printStackTrace()
        }
        val url = "jdbc:oracle:thin:@" + "84.237.50.81:1521" + ":"
        val userName = "20206_Leonteva"
        val pass = "5yqnfmew"

        val timeZone: TimeZone = TimeZone.getTimeZone("GMT+7")
        TimeZone.setDefault(timeZone)
        Locale.setDefault(Locale.ENGLISH)

        var conn: Connection? = null
        try {
            conn = DriverManager.getConnection(url, userName, pass)
            if (conn == null) {
                println("��� ���������� � ��!")
                System.exit(0)
            }
            val stmt: Statement = conn.createStatement()
            val rs = stmt.executeQuery("SELECT * FROM \"Employees\"")
            while (rs.next()) {
                println(
                    rs.row.toString() + ". " + rs.getString("\"FIRST_NAME\"")
                            + "\t" + rs.getString("\"LAST_NAME\"")
                )
            }
            val sql = "select count(*) from \"Employees\""
            val preStatement: PreparedStatement = conn.prepareStatement(sql)
            val result = preStatement.executeQuery()
            while (result.next()) {
                val count = result.getInt(1)
                println("Total number of records in EMP table: $count")
            }
            /**
             * stmt.close();
             * ��� �������� Statement ������������� �����������
             * ��� ��������� � ��� �������� ������� ResultSet
             */
            //  stmt.close();
        } catch (e: SQLException) {
            println("���")
            e.printStackTrace()
        } finally {
            if (conn != null) {
                conn.close()
            }
        }
    }

    private fun setupVmObservers() = viewModel.run {
        navEvent.observe(viewLifecycleOwner, ::obtainNavEvent)
    }

    private fun obtainNavEvent(event: SplashRoutes) = when (event) {
        SplashRoutes.ToLogin -> findNavController().navigate(R.id.toLogin)
        SplashRoutes.ToMainScreen -> findNavController().navigate(R.id.toMainScreen)
    }
}