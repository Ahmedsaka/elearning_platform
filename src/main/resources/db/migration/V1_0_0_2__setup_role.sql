-- Create role table
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[role]') AND type in (N'U'))
    BEGIN
        CREATE TABLE [dbo].[role]
        (
            [id]          BIGINT IDENTITY (1,1) PRIMARY KEY NOT NULL,
            [role_name]    VARCHAR(200)                      NOT NULL ,
            [create_date] DATETIME
        )
    END
GO


-- Create role sp
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_create_role]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_create_role
GO
CREATE PROCEDURE sp_create_role( @id BIGINT=NULL OUTPUT,
                                @role_name VARCHAR(200))
AS
    SET NOCOUNT ON
INSERT INTO [dbo].[role]( role_name, create_date)
VALUES( @role_name, GETDATE())

SELECT @id = SCOPE_IDENTITY()
    RETURN @@Error
GO


-- Create user_role table
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[user_role]') AND type in (N'U'))
    BEGIN
        CREATE TABLE [dbo].[user_role]
        (
            [id]            BIGINT IDENTITY (1,1) PRIMARY KEY NOT NULL,
            [role_id]       BIGINT                FOREIGN KEY REFERENCES dbo.role(id),
            [admin_user_id] BIGINT                FOREIGN KEY REFERENCES dbo.admin_user(id),
        )
    END
GO


-- sp find role by adminUser id
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_find_role_by_user_id]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_find_role_by_user_id
GO
CREATE PROCEDURE sp_find_role_by_user_id(@admin_user_id BIGINT)
AS SET NOCOUNT ON
    BEGIN
        SELECT dbo.role.id, dbo.role.role_name, dbo.role.create_date FROM dbo.role
        INNER JOIN dbo.user_role
            ON dbo.role.id = dbo.user_role.role_id
        WHERE dbo.user_role.admin_user_id = @admin_user_id
    END
    RETURN @@Error
GO



-- sp find role by name
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_find_role_by_name]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_find_role_by_name
GO
CREATE PROCEDURE sp_find_role_by_name(@name VARCHAR(200))
AS SET NOCOUNT ON
BEGIN
    SELECT * FROM dbo.role
    WHERE dbo.role.role_name = @name
END
    RETURN @@Error
GO


-- Create admin adminUser role
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id (N'[dbo].[sp_create_admin_user_role]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
    DROP PROCEDURE sp_create_admin_user_role
GO
CREATE PROCEDURE sp_create_admin_user_role( @id BIGINT=NULL OUTPUT,
                                 @role_id BIGINT,
                                 @admin_user_id BIGINT)
AS
    SET NOCOUNT ON
INSERT INTO [dbo].[user_role]( role_id, admin_user_id)
VALUES( @role_id, @admin_user_id)

SELECT @id = SCOPE_IDENTITY()
    RETURN @@Error
GO