package com.example.jayasales.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.debduttapanda.j3lib.NotificationService
import com.debduttapanda.j3lib.boolState
import com.debduttapanda.j3lib.dep
import com.debduttapanda.j3lib.rememberNotifier
import com.debduttapanda.j3lib.sep
import com.example.jayasales.MyDataIds
import com.example.jayasales.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    notifier: NotificationService = rememberNotifier(),
    openLogoutDialog: Boolean = boolState(key = MyDataIds.opendialog).value,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RoundedCornerShape(0.dep),
                modifier = Modifier
                    .padding(end = 68.dep)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(28.dep))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dep)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dep)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription ="",
                            modifier = Modifier
                                .size(
                                    width = 64.dep,
                                    height = 64.dep
                                )
                                .shadow(2.dep, shape = CircleShape)
                        )
                        Card(
                            colors = CardDefaults.cardColors(Color.White),
                            shape = CircleShape,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(20.dep),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dep
                            )
                        ) {
                            IconButton(
                                onClick = {
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(5.dep)
                                        .size(width = 12.dep, height = 12.dep),
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.Start
                    )
                    {
                        Text(
                            text = "Rakesh Roshan",
                            fontSize = 18.sep,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF222222)
                        )
                        Spacer(modifier = Modifier.height(2.dep))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dep)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription ="",
                                tint = Color(0xFFD62B2B),
                                modifier = Modifier.size(width = 16.dep, height = 20.dep)
                            )
                            Text(
                                text = "Belur Howrah India",
                                fontSize = 12.sep,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dep))
                Divider(
                    modifier = Modifier
                        .height(0.3.dep)
                        .padding(horizontal = 20.dep)
                        .background(color = Color.LightGray)
                )
                Spacer(modifier = Modifier.height(20.dep))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(40.dep)
                        .fillMaxWidth()
                        .clickable {
                            notifier.notify(MyDataIds.returnRequest)
                        }
                ) {
                    Icon(
                        painterResource(id = R.drawable.returnrequest),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                    Spacer(Modifier.width(8.dep))
                    Text(
                        text = stringResource(id = R.string.Return),
                        fontSize = 14.sep,
                        color = Color(0xFF222222)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(40.dep)
                        .fillMaxWidth()
                        .clickable {
                            notifier.notify(MyDataIds.logout)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                    Spacer(Modifier.width(8.dep))
                    Text(
                        text = "Logout",
                        fontSize = 14.sep,
                        color = Color(0xFF222222)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        val openDialog = remember { mutableStateOf(false) }
        if (openLogoutDialog) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(
                        text = "Login",
                    )
                },
                text = {
                    Text("Are you sure to logout")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            notifier.notify(MyDataIds.Confirm)
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFB81B1B))
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            notifier.notify(MyDataIds.dismiss)
                        }) {
                        Text("Dismiss")
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "",
                        tint = Color(0xFFB81B1B),
                        modifier = Modifier
                            //.padding(start = 16.dp)
                            .size(24.dep)
                    )
                }
            )
        }
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier
                        .shadow(2.dep),
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.jayasales),
                            contentDescription = stringResource(id = R.string.logo),
                            modifier = Modifier
                                //.padding(end = 35.dep)
                                .height(60.dp)
                                .width(100.dp),
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } },
                            modifier = Modifier

                                .height(20.dep)
                                .width(32.dep)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.HamBurger),
                                tint = Color(0xFF191919)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(Color(0xFFFFEB56)),
                    actions = {
                        IconButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .height(36.dep)
                                .width(40.dep)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.notification),
                                contentDescription = stringResource(id = R.string.notification),
                                tint = Color(0xFF191919)
                            )
                        }
                    }
                )
            },
        )
        {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .background(Color(0xFF015AB6))
                        .height(44.dep)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dep)
                        .clickable {
                            notifier.notify(MyDataIds.route)
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.route),
                            contentDescription = "",
                            modifier = Modifier
                                .height(24.dep)
                                .width(28.dep),
                        )
                        Spacer(modifier = Modifier.width(12.dep))
                        Text(
                            text = stringResource(id = R.string.route),
                            fontSize = 14.sep,
                            color = Color.White
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.height(20.dep))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 20.dep)
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .background(Color(0xFFD62B2B), RoundedCornerShape(4.dep))
                            .height(112.dep)
                            .weight(.5f)
                    ) {
                        Text(
                            text = "₹15,000",
                            fontSize = 26.sep,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                        )
                        Text(
                            text = stringResource(id = R.string.payments),
                            fontSize = 16.sep,
                            color = Color.White,
                            modifier = Modifier
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dep))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .background(Color(0xFF6A9E73), RoundedCornerShape(4.dep))
                            .height(112.dep)
                            .weight(.5f)
                    ) {
                        Text(
                            text = "1,000",
                            fontSize = 26.sep,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                        )
                        Text(
                            text = stringResource(id = R.string.sales),
                            fontSize = 16.sep,
                            color = Color.White,
                            modifier = Modifier
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dep))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dep)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(.25f)
                    ) {
                        IconButton(
                            onClick = {
                                notifier.notify(MyDataIds.parties)
                            },
                            modifier = Modifier
                                .width(72.dep)
                                .height(72.dep)
                                .shadow(4.dep, shape = RoundedCornerShape(50.dep), clip = true)
                                .background(Color.White)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.parties),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(40.dep)
                                    .height(32.dep)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dep))
                        Text(
                            text = stringResource(id = R.string.Parties),
                            fontSize = 12.sep,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF222222)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dep))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(.25f)
                    ) {
                        IconButton(
                            onClick = {
                                notifier.notify(MyDataIds.items)
                            },
                            modifier = Modifier
                                .width(72.dep)
                                .height(72.dep)
                                .shadow(4.dep, shape = RoundedCornerShape(50.dep), clip = true)
                                .background(Color.White)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.items),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(40.dep)
                                    .height(32.dep)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dep))
                        Text(
                            text = stringResource(id = R.string.Items),
                            fontSize = 12.sep,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF222222)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dep))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(.25f)
                    ) {
                        IconButton(
                            onClick = {
                                notifier.notify(MyDataIds.transactions)
                            },
                            modifier = Modifier
                                .width(72.dep)
                                .height(72.dep)
                                .shadow(4.dep, shape = RoundedCornerShape(50.dep), clip = true)
                                .background(Color.White)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.transactions),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(40.dep)
                                    .height(32.dep)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dep))
                        Text(
                            text = stringResource(id = R.string.Transactions),
                            fontSize = 12.sep,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF222222)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dep))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(.25f)
                    ) {
                        IconButton(
                            onClick = {
                                notifier.notify(MyDataIds.markAttendance)
                            },
                            modifier = Modifier
                                .width(72.dep)
                                .height(72.dep)
                                .shadow(4.dep, shape = RoundedCornerShape(50.dep), clip = true)
                                .background(Color.White)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.attendance),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(40.dep)
                                    .height(32.dep)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dep))
                        Text(
                            text = stringResource(id = R.string.Attendance),
                            fontSize = 12.sep,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF222222)
                        )
                    }
                }
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .padding(bottom = 20.dep)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Have Question ? Message us on\n987-654-3210",
                    fontSize = 12.sep,
                    textAlign = TextAlign.Center,
                    color = Color(0xFFC7C7C7)
                )
            }
        }
    }
}